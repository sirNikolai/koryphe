/*
 * Copyright 2019-2020 Crown Copyright
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
import uk.gov.gchq.koryphe.util.JsonSerialiser;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DeserialiseXmlTest extends FunctionTest<DeserialiseXml> {
    @Override
    protected DeserialiseXml getInstance() {
        return new DeserialiseXml();
    }

    @Override
    protected Iterable<DeserialiseXml> getDifferentInstancesOrNull() {
        return null;
    }

    @Override
    protected Class[] getExpectedSignatureInputClasses() {
        return new Class[] {String.class};
    }

    @Override
    protected Class[] getExpectedSignatureOutputClasses() {
        return new Class[] {Map.class};
    }

    @Test
    @Override
    public void shouldJsonSerialiseAndDeserialise() throws IOException {
        // Given
        final DeserialiseXml function = new DeserialiseXml();

        // When
        final String json = JsonSerialiser.serialise(function);

        // Then
        JsonSerialiser.assertEquals(String.format("{%n" +
                "   \"class\" : \"uk.gov.gchq.koryphe.impl.function.DeserialiseXml\"%n" +
                "}"), json);
    }

    @Test
    public void shouldParseXml() {
        // Given
        final DeserialiseXml function = new DeserialiseXml();
        final String input = "<root><element1><element2 attr=\"attr1\">value1</element2></element1><element1><element2>value2</element2></element1></root>";

        // When
        Map<String, Object> result = function.apply(input);

        // Then
        Map<String, Object> element2aMap = new HashMap<>();
        Map<String, Object> element2aAttrContentMap = new HashMap<>();
        element2aAttrContentMap.put("attr", "attr1");
        element2aAttrContentMap.put("content", "value1");
        element2aMap.put("element2", element2aAttrContentMap);
        Map<String, Object> element2bMap = new HashMap<>();
        element2bMap.put("element2", "value2");
        HashMap<Object, Object> element1Map = new HashMap<>();
        element1Map.put("element1", Arrays.asList(element2aMap, element2bMap));
        HashMap<Object, Object> expectedRootMap = new HashMap<>();
        expectedRootMap.put("root", element1Map);
        assertEquals(expectedRootMap, result);
    }

    @Test
    public void shouldReturnNullForNullInput() {
        // Given
        final DeserialiseXml function = new DeserialiseXml();

        // When
        Map<String, Object> result = function.apply(null);

        // Then
        assertNull(result);
    }
}
