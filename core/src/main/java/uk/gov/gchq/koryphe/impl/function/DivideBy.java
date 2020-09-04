/*
 * Copyright 2017-2020 Crown Copyright
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
import uk.gov.gchq.koryphe.tuple.n.Tuple2;

/**
 * A <code>DivideBy</code> is a {@link java.util.function.Function} that takes in
 * an {@link Integer} and returns the result of dividing this integer by a pre-configured
 * value.
 * <p>
 * The resulting object is a {@link Tuple2} containing the quotient and remainder.
 */
@Since("1.0.0")
@Summary("Takes in an integer and returns the result of dividing this by a pre-configured value.")
public class DivideBy extends KorypheFunction<Integer, Tuple2<Integer, Integer>> {
    private int by = 1;

    public DivideBy() {
    }

    public DivideBy(final int by) {
        setBy(by);
    }

    public int getBy() {
        return by;
    }

    public void setBy(final int by) {
        this.by = by;
    }

    @Override
    public Tuple2<Integer, Integer> apply(final Integer input) {
        if (input == null) {
            return null;
        } else {
            return new Tuple2<>(input / by, input % by);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (!super.equals(o)) {
            return false; // Does class checking
        }

        DivideBy that = (DivideBy) o;
        return new EqualsBuilder()
                .append(by, that.by)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(47, 59)
                .appendSuper(super.hashCode())
                .append(by)
                .toHashCode();
    }
}
