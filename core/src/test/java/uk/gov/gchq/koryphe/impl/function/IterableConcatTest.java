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

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import uk.gov.gchq.koryphe.function.FunctionTest;
import uk.gov.gchq.koryphe.util.JsonSerialiser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IterableConcatTest extends FunctionTest<IterableConcat> {
    @Override
    protected IterableConcat getInstance() {
        return new IterableConcat();
    }

    @Override
    protected Iterable<IterableConcat> getDifferentInstancesOrNull() {
        return null;
    }

    @Override
    protected Class[] getExpectedSignatureInputClasses() {
        return new Class[] {Iterable.class};
    }

    @Override
    protected Class[] getExpectedSignatureOutputClasses() {
        return new Class[] {Iterable.class};
    }

    @Test
    @Override
    public void shouldJsonSerialiseAndDeserialise() throws IOException {
        // Given
        final IterableConcat function = new IterableConcat();

        // When
        final String json = JsonSerialiser.serialise(function);

        // Then
        JsonSerialiser.assertEquals(String.format("{%n" +
                "   \"class\" : \"uk.gov.gchq.koryphe.impl.function.IterableConcat\"%n" +
                "}"), json);

        // When 2
        final IterableConcat deserialised = JsonSerialiser.deserialise(json, IterableConcat.class);

        // Then 2
        assertNotNull(deserialised);
    }

    @Test
    public void shouldFlattenNestedIterables() {
        // Given
        final IterableConcat<Integer> function = new IterableConcat<>();

        // When
        final Iterable<Integer> result = function.apply(Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6)));

        // Then
        assertNotNull(result);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6), Lists.newArrayList(result));
    }

    @Test
    public void shouldHandleNullInputIterable() {
        // Given
        final IterableConcat<Integer> function = new IterableConcat<>();

        // When / Then
        final Exception exception = assertThrows(IllegalArgumentException.class, () -> function.apply(null));
        assertEquals("iterables are required", exception.getMessage());
    }

    @Test
    public void shouldReturnEmptyIterableForEmptyInput() {
        // Given
        final IterableConcat<Integer> function = new IterableConcat<>();
        final Iterable<Iterable<Integer>> input = new ArrayList<>();

        // When
        final Iterable<Integer> results = function.apply(input);

        // Then
        assertTrue(Iterables.isEmpty(results));
    }

    @Test
    public void shouldHandleNullElementsOfInnerIterable() {
        // Given
        final IterableConcat<Integer> function = new IterableConcat<>();
        final Iterable<Iterable<Integer>> input = Arrays.asList(
                Arrays.asList(1, 2, null, 4),
                Arrays.asList(5, null, 7));

        // When
        final Iterable<Integer> results = function.apply(input);

        // Then
        assertEquals(Arrays.asList(1, 2, null, 4, 5, null, 7), Lists.newArrayList(results));
    }
}
