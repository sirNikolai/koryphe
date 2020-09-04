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

import org.junit.jupiter.api.Test;

import uk.gov.gchq.koryphe.binaryoperator.BinaryOperatorTest;
import uk.gov.gchq.koryphe.util.JsonSerialiser;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MaxTest extends BinaryOperatorTest<Max> {

    @Test
    public void testAggregateInIntMode() {
        // Given
        final Max max = new Max();

        // When 1
        Comparable state = max.apply(1, null);

        // Then 1
        assertTrue(state instanceof Integer);
        assertEquals(1, state);

        // When 2
        state = max.apply(3, state);

        // Then 2
        assertTrue(state instanceof Integer);
        assertEquals(3, state);

        // When 3
        state = max.apply(2, state);

        // Then 3
        assertTrue(state instanceof Integer);
        assertEquals(3, state);
    }

    @Test
    public void testAggregateInLongMode() {
        // Given
        final Max max = new Max();

        // When 1
        Comparable state = max.apply(2L, null);

        // Then 1
        assertTrue(state instanceof Long);
        assertEquals(2L, state);

        // When 2
        state = max.apply(1L, state);

        // Then 2
        assertTrue(state instanceof Long);
        assertEquals(2L, state);

        // When 3
        state = max.apply(3L, state);

        // Then 3
        assertTrue(state instanceof Long);
        assertEquals(3L, state);
    }

    @Test
    public void testAggregateInDoubleMode() {
        // Given
        final Max max = new Max();

        // When 1
        Comparable state = max.apply(1.1d, null);

        // Then 1
        assertTrue(state instanceof Double);
        assertEquals(1.1d, state);

        // When 2
        state = max.apply(2.1d, state);

        // Then 2
        assertTrue(state instanceof Double);
        assertEquals(2.1d, state);

        // When 3
        state = max.apply(1.5d, state);

        // Then 3
        assertTrue(state instanceof Double);
        assertEquals(2.1d, state);
    }

    @Test
    public void testAggregateMixedInput() {
        // Given
        final Max max = new Max();

        // When 1
        final Comparable newState = max.apply(null, 1);

        // When 2
        assertThrows(ClassCastException.class, () -> max.apply(newState, 3L));

        // When 3
        assertThrows(ClassCastException.class, () -> max.apply(newState, 2.1d));

        // Then 3
        assertTrue(newState instanceof Integer);
        assertEquals(1, newState);
    }

    @Test
    public void shouldJsonSerialiseAndDeserialise() throws IOException {
        // Given
        final Max aggregator = new Max();

        // When 1
        final String json = JsonSerialiser.serialise(aggregator);

        // Then 1
        JsonSerialiser.assertEquals(String.format("{%n" +
                "  \"class\" : \"uk.gov.gchq.koryphe.impl.binaryoperator.Max\"%n" +
                "}"), json);

        // When 2
        final Max deserialisedAggregator = JsonSerialiser.deserialise(json, Max.class);

        // Then 2
        assertNotNull(deserialisedAggregator);
    }

    @Override
    protected Max getInstance() {
        return new Max();
    }

    @Override
    protected Iterable<Max> getDifferentInstancesOrNull() {
        return null;
    }
}
