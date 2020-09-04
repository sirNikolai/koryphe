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

package uk.gov.gchq.koryphe.binaryoperator;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import uk.gov.gchq.koryphe.Since;
import uk.gov.gchq.koryphe.Summary;
import uk.gov.gchq.koryphe.adapted.Adapted;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * An {@link Adapted} {@link BinaryOperator}.
 *
 * @param <T>  Input/Output type
 * @param <OT> Input/Output type of the BinaryOperator being applied
 */
@Since("1.0.0")
@Summary("Applies a function and adapts the input/output")
public class AdaptedBinaryOperator<T, OT> extends Adapted<T, OT, OT, T, T> implements BinaryOperator<T> {

    protected BinaryOperator<OT> binaryOperator;

    /**
     * Default - for serialisation
     */
    public AdaptedBinaryOperator() {
    }

    public AdaptedBinaryOperator(final BinaryOperator<OT> binaryOperator,
                                 final Function<T, OT> inputAdapter,
                                 final BiFunction<T, OT, T> outputAdapter) {
        setBinaryOperator(binaryOperator);
        setInputAdapter(inputAdapter);
        setOutputAdapter(outputAdapter);
    }

    public AdaptedBinaryOperator(final BinaryOperator<OT> binaryOperator,
                                 final Function<T, OT> inputAdapter,
                                 final Function<OT, T> outputAdapter) {
        setBinaryOperator(binaryOperator);
        setInputAdapter(inputAdapter);
        setOutputAdapter(outputAdapter);
    }

    /**
     * Apply the BinaryOperator by adapting the input and outputs.
     *
     * @param state Value to fold into
     * @param input New input to fold in
     * @return New state
     */
    @Override
    public T apply(final T state, final T input) {
        if (binaryOperator == null) {
            throw new IllegalArgumentException("BinaryOperator cannot be null");
        }
        return adaptOutput(binaryOperator.apply(adaptInput(state), adaptInput(input)), state);
    }

    public BinaryOperator<OT> getBinaryOperator() {
        return binaryOperator;
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
    public void setBinaryOperator(final BinaryOperator<OT> binaryOperator) {
        this.binaryOperator = binaryOperator;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (!super.equals(o)) {
            return false; // Does class checking
        }

        final AdaptedBinaryOperator that = (AdaptedBinaryOperator) o;
        return new EqualsBuilder()
                .append(binaryOperator, that.binaryOperator)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(43, 67)
                .appendSuper(super.hashCode())
                .append(binaryOperator)
                .toHashCode();
    }
}
