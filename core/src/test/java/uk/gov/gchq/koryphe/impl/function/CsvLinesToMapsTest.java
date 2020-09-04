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

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import uk.gov.gchq.koryphe.function.FunctionTest;
import uk.gov.gchq.koryphe.util.JsonSerialiser;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CsvLinesToMapsTest extends FunctionTest<CsvLinesToMaps> {
    @Override
    protected CsvLinesToMaps getInstance() {
        return new CsvLinesToMaps();
    }

    @Override
    protected Iterable<CsvLinesToMaps> getDifferentInstancesOrNull() {
        return Arrays.asList(
                new CsvLinesToMaps().delimiter('\t'),
                new CsvLinesToMaps().firstRow(8),
                new CsvLinesToMaps().quoteChar('\''),
                new CsvLinesToMaps().quoted(),
                new CsvLinesToMaps().header("myHeader")
        );
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
        final CsvLinesToMaps function = new CsvLinesToMaps()
                .header("header1", "header2", "header3")
                .delimiter('|')
                .firstRow(2)
                .quoteChar('\'')
                .quoted();

        // When
        final String json = JsonSerialiser.serialise(function);

        // Then
        JsonSerialiser.assertEquals(String.format("{%n" +
                "   \"class\" : \"uk.gov.gchq.koryphe.impl.function.CsvLinesToMaps\",%n" +
                "   \"header\" : [\"header1\", \"header2\", \"header3\"]," +
                "   \"delimiter\" : \"|\"," +
                "   \"firstRow\" : 2," +
                "   \"quoteChar\" : \"'\"," +
                "   \"quoted\" : true" +
                "}"), json);
    }

    @Test
    public void shouldParseCsvWithHeader() {
        // Given
        final CsvLinesToMaps function = new CsvLinesToMaps().header("header1", "header2", "header3").firstRow(1);
        final List<String> input = Arrays.asList(
                "header1,header2,header3",
                "value1,value2,value3"
        );

        // When
        final Iterable<Map<String, Object>> result = function.apply(input);

        // Then
        final HashMap<Object, Object> expected = new HashMap<>();
        expected.put("header1", "value1");
        expected.put("header2", "value2");
        expected.put("header3", "value3");
        assertEquals(Collections.singletonList(expected), Lists.newArrayList(result));
    }

    @Test
    public void shouldReturnNullForNullInput() {
        // Given
        final CsvLinesToMaps function = new CsvLinesToMaps();
        final List<String> input = null;

        // When
        final Object result = function.apply(input);

        // Then
        assertNull(result);
    }
}
