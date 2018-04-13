/*
 * Copyright 2017-2018 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.gchq.koryphe.impl.predicate.range;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import uk.gov.gchq.koryphe.Since;
import uk.gov.gchq.koryphe.Summary;
import uk.gov.gchq.koryphe.util.TimeUnit;

import java.util.Date;

/**
 * <p>
 * An <code>InDateRangeDual</code> is a {@link java.util.function.Predicate}
 * that tests if a start {@link Comparable} and end {@link Comparable} is
 * within a provided range [start, end]. Specifically the start {@link Comparable}
 * has to be greater than the start bound and the end {@link Comparable} has to
 * be less than the end bound.
 * By default the range is inclusive, you can toggle this using the startInclusive
 * and endInclusive booleans.
 * </p>
 * <p>
 * If the start is not set then this will be treated as unbounded.
 * Similarly with the end.
 * </p>
 * <p>
 * If the test value is null then the predicate will return false.
 * </p>
 * <p>
 * This range predicate takes 2 values to test, if you want to test
 * a single value lies within a range then you can use the
 * {@link InDateRange} predicate.
 * </p>
 * <p>
 * The range can also be configured using time offsets
 * from the current system time or a provided start/end time.
 * You can set the start and end offsets using startOffset and endOffset.
 * By default the offset is measured in Days, this can be changed to
 * DAY, HOUR, MINUTE, SECOND and MILLISECOND using the offsetUnit field.
 * <p>
 * At the point when test is called on the class the
 * current system time is used to calculate the start and end values based on:
 * System.currentTimeMillis() + offset.
 * </p>
 * <p>
 * You can configure the start and end time strings using one of the following formats:
 * </p>
 * <ul>
 * <li>timestamp in milliseconds</li>
 * <li>yyyy/MM</li>
 * <li>yyyy/MM/dd</li>
 * <li>yyyy/MM/dd HH</li>
 * <li>yyyy/MM/dd HH:mm</li>
 * <li>yyyy/MM/dd HH:mm:ss</li>
 * </ul>
 * You can use a space, '-', '/', '_', ':', '|', or '.' to separate the parts.
 *
 * @see Builder
 */
@JsonPropertyOrder(value = {"start", "startOffset", "end", "endOffset", "startInclusive", "endInclusive", "offsetUnit"}, alphabetic = true)
@JsonDeserialize(builder = InDateRangeDual.Builder.class)
@Since("1.1.0")
@Summary("Tests if a start and end comparable are within a provided date range [start, end].")
public class InDateRangeDual extends AbstractInTimeRangeDual<Date> {
    public InDateRangeDual() {
        super(Date::new);
    }

    public static class Builder extends BaseBuilder<Builder, InDateRangeDual, Date> {
        public Builder() {
            super(new InDateRangeDual());
        }

        @Override
        public Builder timeUnit(final TimeUnit timeUnit) {
            if (!TimeUnit.MILLISECOND.equals(timeUnit)) {
                throw new IllegalArgumentException("timeUnit must be set to " + TimeUnit.MILLISECOND);
            }
            return this;
        }
    }
}
