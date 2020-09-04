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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegexTest extends PredicateTest<Regex> {

    @Test
    public void shouldAccepValidValue() {
        // Given
        final Regex filter = new Regex("te[a-d]{3}st");

        // When
        boolean accepted = filter.test("teaadst");

        // Then
        assertTrue(accepted);
    }

    @Test
    public void shouldRejectInvalidValue() {
        // Given
        final Regex filter = new Regex("fa[a-d]{3}il");

        // When
        boolean accepted = filter.test("favcdil");

        // Then
        assertFalse(accepted);
    }

    @Test
    public void shouldJsonSerialiseAndDeserialise() throws IOException {
        // Given
        final Regex filter = new Regex("test");

        // When
        final String json = JsonSerialiser.serialise(filter);

        // Then
        JsonSerialiser.assertEquals(String.format("{%n" +
                "  \"class\" : \"uk.gov.gchq.koryphe.impl.predicate.Regex\",%n" +
                "  \"value\" : {%n"
                + "    \"java.util.regex.Pattern\" : \"test\"%n"
                + "  }%n" +
                "}"), json);

        // When 2
        final Regex deserialisedFilter = JsonSerialiser.deserialise(json, Regex.class);

        // Then 2
        assertEquals(filter.getControlValue().pattern(), deserialisedFilter.getControlValue().pattern());
        assertNotNull(deserialisedFilter);
    }

    @Override
    protected Regex getInstance() {
        return new Regex("[a-zA-Z]{1,12}");
    }

    @Override
    protected Iterable<Regex> getDifferentInstancesOrNull() {
        return Arrays.asList(
                new Regex(),
                new Regex("different")
        );
    }

    }
