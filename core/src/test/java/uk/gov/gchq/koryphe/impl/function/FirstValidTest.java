/*
 * Copyright 2018-2019 Crown Copyright
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

import org.junit.jupiter.api.Test;

import uk.gov.gchq.koryphe.function.FunctionTest;
import uk.gov.gchq.koryphe.impl.predicate.IsLessThan;
import uk.gov.gchq.koryphe.impl.predicate.IsMoreThan;
import uk.gov.gchq.koryphe.util.JsonSerialiser;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FirstValidTest extends FunctionTest<FirstValid> {

    @Test
    public void shouldApplyKeyPredicate() {
        // Given
        final List<Integer> items = Arrays.asList(1, 2, 3);

        final FirstValid<Integer> predicate =
                new FirstValid<Integer>()
                        .setPredicate(new IsMoreThan(1));

        // When
        final Integer result = predicate.apply(items);

        // Then
        assertEquals(Integer.valueOf(2), result);
    }

    @Test
    public void shouldSkipNullPredicate() {
        // Given
        final List<Integer> items = Arrays.asList(1, 2, 3);

        final FirstValid<Integer> predicate =
                new FirstValid<Integer>()
                        .predicate(null);

        // When
        final Integer result = predicate.apply(items);

        // Then
        assertNull(result);
    }

    @Test
    public void shouldReturnNullForNullPredicate() {
        // Given
        final FirstValid<Integer> predicate =
                new FirstValid<Integer>()
                        .predicate(new IsMoreThan(1));

        // When
        final Integer result = predicate.apply(null);

        // Then
        assertNull(result);
    }

    @Override
    protected FirstValid<Integer> getInstance() {
        return new FirstValid<>(new IsMoreThan(1));
    }

    @Override
    protected Iterable<FirstValid> getDifferentInstancesOrNull() {
        return Arrays.asList(
                new FirstValid<>(new IsMoreThan(4)),
                new FirstValid<>(new IsLessThan(3))
        );
    }

    @Override
    protected Class[] getExpectedSignatureInputClasses() {
        return new Class[] {Iterable.class};
    }

    @Override
    protected Class[] getExpectedSignatureOutputClasses() {
        return new Class[] {Object.class};
    }

    @Test
    @Override
    public void shouldJsonSerialiseAndDeserialise() throws IOException {
        // Given
        final FirstValid predicate = getInstance();

        // When
        final String json = JsonSerialiser.serialise(predicate);

        // Then
        JsonSerialiser.assertEquals("{" +
                "\"class\":\"uk.gov.gchq.koryphe.impl.function.FirstValid\"," +
                "\"predicate\":{\"class\":\"uk.gov.gchq.koryphe.impl.predicate.IsMoreThan\",\"orEqualTo\":false,\"value\":1}" +
                "}", json);

        // When 2
        final FirstValid deserialised = JsonSerialiser.deserialise(json, FirstValid.class);

        // Then 2
        assertNotNull(deserialised);
        assertEquals(1, ((IsMoreThan) deserialised.getPredicate()).getControlValue());
    }
}
