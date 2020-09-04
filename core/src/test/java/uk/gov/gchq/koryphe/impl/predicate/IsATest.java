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

package uk.gov.gchq.koryphe.impl.predicate;

import org.junit.jupiter.api.Test;

import uk.gov.gchq.koryphe.predicate.PredicateTest;
import uk.gov.gchq.koryphe.util.JsonSerialiser;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IsATest extends PredicateTest<IsA> {

    @Test
    public void shouldAcceptTheValueWhenSameClass() {
        // Given
        final IsA predicate = new IsA(String.class);

        // When
        boolean valid = predicate.test("Test");

        // Then
        assertTrue(valid);
    }

    @Test
    public void shouldRejectTheValueWhenDifferentClasses() {
        // Given
        final IsA predicate = new IsA(String.class);

        // When
        boolean valid = predicate.test(5);

        // Then
        assertFalse(valid);
    }

    @Test
    public void shouldAcceptTheValueWhenSuperClass() {
        // Given
        final IsA predicate = new IsA(Number.class);

        // When
        boolean valid = predicate.test(5);

        // Then
        assertTrue(valid);
    }

    @Test
    public void shouldAcceptTheValueWhenNullValue() {
        // Given
        final IsA predicate = new IsA(String.class);

        // When
        boolean valid = predicate.test(null);

        // Then
        assertTrue(valid);
    }

    @Test
    public void shouldCreateIsAFromClassName() {
        // Given
        final String type = "java.lang.String";

        // When
        final IsA predicate = new IsA(type);

        // Then
        assertNotNull(predicate);
        assertEquals(predicate.getType(), type);
    }

    @Test
    public void shouldThrowExceptionIfInvalidClassNameProvided() {
        // Given
        final String type = "java.util.String";

        // When
        assertThrows(IllegalArgumentException.class, () -> new IsA(type));
    }

    @Test
    public void shouldJsonSerialiseAndDeserialise() throws IOException {
        // Given
        final Class<Integer> type = Integer.class;
        final IsA filter = new IsA(type);

        // When
        final String json = JsonSerialiser.serialise(filter);

        // Then
        assertEquals("{\"class\":\"uk.gov.gchq.koryphe.impl.predicate.IsA\",\"type\":\"java.lang.Integer\"}", json);

        // When 2
        final IsA deserialisedFilter = JsonSerialiser.deserialise(json, IsA.class);

        // Then 2
        assertNotNull(deserialisedFilter);
        assertEquals(type.getName(), deserialisedFilter.getType());
    }

    @Override
    protected IsA getInstance() {
        return new IsA(String.class);
    }

    @Override
    protected Iterable<IsA> getDifferentInstancesOrNull() {
        return Arrays.asList(
                new IsA(),
                new IsA(Long.class)
        );
    }
}
