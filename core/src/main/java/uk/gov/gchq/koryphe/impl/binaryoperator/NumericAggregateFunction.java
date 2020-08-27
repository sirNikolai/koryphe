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

package uk.gov.gchq.koryphe.impl.binaryoperator;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import uk.gov.gchq.koryphe.binaryoperator.KorypheBinaryOperator;

/**
 * A <code>NumericAggregateFunction</code> is a {@link KorypheBinaryOperator} that takes in
 * {@link Number}s of the same type and processes the number in some way. To implement this class just
 * implement the init methods and aggregate methods for the different number types.
 * If you know the type of number that will be used then this can be set by calling setMode(NumberType),
 * otherwise it will be automatically set for you using the class of the first number passed in.
 *
 * @see NumericAggregateFunction
 */
public abstract class NumericAggregateFunction extends KorypheBinaryOperator<Number> {
    @SuppressFBWarnings(value = "BC_UNCONFIRMED_CAST", justification = "Assume both inputs are the same type")
    @Override
    public Number _apply(final Number a, final Number b) {
        if (a instanceof Integer) {
            return aggregateInt((Integer) a, (Integer) b);
        } else if (a instanceof Long) {
            return aggregateLong((Long) a, (Long) b);
        } else if (a instanceof Double) {
            return aggregateDouble((Double) a, (Double) b);
        } else if (a instanceof Float) {
            return aggregateFloat((Float) a, (Float) b);
        } else if (a instanceof Short) {
            return aggregateShort((Short) a, (Short) b);
        }

        return null;
    }

    protected abstract Integer aggregateInt(Integer a, Integer b);

    protected abstract Long aggregateLong(Long a, Long b);

    protected abstract Double aggregateDouble(Double a, Double b);

    protected abstract Float aggregateFloat(Float a, Float b);

    protected abstract Short aggregateShort(Short a, Short b);
}
