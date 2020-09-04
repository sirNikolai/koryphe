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

package uk.gov.gchq.koryphe.predicate;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import uk.gov.gchq.koryphe.Since;
import uk.gov.gchq.koryphe.Summary;
import uk.gov.gchq.koryphe.adapted.InputAdapted;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * An {@link InputAdapted} {@link Predicate}.
 *
 * @param <I>  Input type
 * @param <PI> Adapted input type for predicate
 */
@Since("1.0.0")
@Summary("Applies a predicate and adapts the input")
public class AdaptedPredicate<I, PI> extends InputAdapted<I, PI> implements Predicate<I> {

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
    protected Predicate<PI> predicate;

    /**
     * Default - for serialisation.
     */
    public AdaptedPredicate() {
    }

    public AdaptedPredicate(final Function<I, PI> inputAdapter, final Predicate<PI> predicate) {
        setInputAdapter(inputAdapter);
        setPredicate(predicate);
    }

    /**
     * Apply the Predicate by adapting the input.
     *
     * @param input Input to adapt and apply predicate to
     * @return Predicate result
     */
    @Override
    public boolean test(final I input) {
        return null == predicate || predicate.test(adaptInput(input));
    }

    public Predicate<PI> getPredicate() {
        return predicate;
    }

    public void setPredicate(final Predicate<PI> predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (!super.equals(o)) {
            return false; // Does class checking
        }

        final AdaptedPredicate that = (AdaptedPredicate) o;
        return new EqualsBuilder()
                .append(predicate, that.predicate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(43, 67)
                .appendSuper(super.hashCode())
                .append(predicate)
                .toHashCode();
    }
}
