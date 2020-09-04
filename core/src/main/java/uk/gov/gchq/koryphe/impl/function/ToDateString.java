/*
 * Copyright 2019-2020 Crown Copyright
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

package uk.gov.gchq.koryphe.impl.function;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import uk.gov.gchq.koryphe.Since;
import uk.gov.gchq.koryphe.Summary;
import uk.gov.gchq.koryphe.function.KorypheFunction;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.util.Objects.isNull;

/**
 * A <code>ToDateString</code> is a {@link java.util.function.Function} that
 * converts a {@link Date} into a {@link String} using the provided format.
 */
@Since("1.8.0")
@Summary("Converts a date to a string")
public class ToDateString extends KorypheFunction<Date, String> {
    private String format;

    public ToDateString() {
    }

    public ToDateString(final String format) {
        this.format = format;
    }

    @Override
    public String apply(final Date date) {
        if (isNull(date)) {
            return null;
        }

        if (isNull(format)) {
            return date.toInstant().toString();
        }

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(final String format) {
        this.format = format;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (!super.equals(o)) {
            return false; // Does exact equals and class checking
        }

        ToDateString that = (ToDateString) o;
        return new EqualsBuilder()
                .append(format, that.format)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(61, 47)
                .appendSuper(super.hashCode())
                .append(format)
                .toHashCode();
    }
}
