/*
 * Copyright 2018-2020 Crown Copyright
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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import uk.gov.gchq.koryphe.function.FunctionTest;
import uk.gov.gchq.koryphe.util.JsonSerialiser;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CastTest extends FunctionTest<Cast> {

    @Disabled
    @Test
    public void shouldCast() {
        // Given
        final Cast function = new Cast(Integer.class);

        // When
        Object output = function.apply(new Long(5));

        // Then
        assertEquals(Integer.class, output.getClass());
    }

    @Override
    protected Cast getInstance() {
        return new Cast<>();
    }

    @Override
    protected Iterable<Cast> getDifferentInstancesOrNull() {
        return Collections.singletonList(new Cast<>(Integer.class));
    }

    @Override
    protected Class[] getExpectedSignatureInputClasses() {
        return new Class[] {Object.class};
    }

    @Override
    protected Class[] getExpectedSignatureOutputClasses() {
        return new Class[] {Object.class};
    }

    @Test
    @Override
    public void shouldJsonSerialiseAndDeserialise() throws IOException {
        // Given
        final Cast function = new Cast<>(String.class);

        // When
        final String json = JsonSerialiser.serialise(function);

        // Then
        JsonSerialiser.assertEquals(String.format("{%n" +
                "  \"class\" : \"uk.gov.gchq.koryphe.impl.function.Cast\",%n" +
                "  \"outputClass\" : \"java.lang.String\"%n" +
                "}"), json);

        // When 2
        final Cast deserialisedMethod = JsonSerialiser.deserialise(json, Cast.class);

        // Then 2
        assertNotNull(deserialisedMethod);
    }
}
