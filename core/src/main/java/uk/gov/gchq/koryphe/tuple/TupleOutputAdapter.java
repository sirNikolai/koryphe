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

package uk.gov.gchq.koryphe.tuple;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import uk.gov.gchq.koryphe.Since;
import uk.gov.gchq.koryphe.Summary;

import java.util.Arrays;
import java.util.function.BiFunction;

/**
 * @param <R>  The type of reference used by tuples.
 * @param <FO> The adapted output type.
 */
@Since("1.0.0")
@Summary("Projects items to a tuple")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
public class TupleOutputAdapter<R, FO> implements BiFunction<Tuple<R>, FO, Tuple<R>> {
    private R[] projection;

    /**
     * Create a new <code>TupleMask</code>.
     */
    public TupleOutputAdapter() {
        projection = (R[]) new Object[0];
    }

    /**
     * Create a new <code>TupleMask</code> with the given field references.
     *
     * @param projection Field references.
     */
    public TupleOutputAdapter(final R[] projection) {
        setProjection(projection);
    }

    @Override
    public Tuple<R> apply(final Tuple<R> state, final FO output) {
        if (null == projection) {
            throw new IllegalArgumentException("Projection is required");
        }

        if (null != state) {
            if (1 == projection.length) {
                state.put(projection[0], output);
            } else {
                int i = 0;
                for (final Object obj : (Iterable) output) {
                    state.put(projection[i++], obj);
                }
            }
        }

        return state;
    }

    /**
     * Set this <code>TupleMask</code> to refer to a tuple of field references.
     *
     * @param projection Field references.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Cloning the array would be expensive - we will have to reply on users not modifying the array")
    public void setProjection(final R[] projection) {
        if (null == projection) {
            this.projection = (R[]) new Object[0];
        } else {
            this.projection = projection;
        }
    }

    /**
     * @return Field references.
     */
    public R[] getProjection() {
        return Arrays.copyOf(projection, projection.length);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || !getClass().equals(o.getClass())) {
            return false;
        }

        final TupleOutputAdapter that = (TupleOutputAdapter) o;
        return new EqualsBuilder()
                .append(projection, that.projection)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(43, 67)
                .append(getClass().hashCode())
                .append(projection)
                .toHashCode();
    }
}
