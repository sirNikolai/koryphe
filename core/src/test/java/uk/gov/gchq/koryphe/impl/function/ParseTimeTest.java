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
import uk.gov.gchq.koryphe.util.TimeUnit;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ParseTimeTest extends FunctionTest<ParseTime> {
    @Override
    protected ParseTime getInstance() {
        return new ParseTime().timeZone("UTC");
    }

    @Override
    protected Iterable<ParseTime> getDifferentInstancesOrNull() {
        return Arrays.asList(
                new ParseTime().format("dd-MMM-YYYY hh:mm:ss"),
                new ParseTime().timeZone("PST"),
                new ParseTime().timeUnit(TimeUnit.DAY)
        );
    }

    @Override
    protected Class[] getExpectedSignatureInputClasses() {
        return new Class[] { String.class };
    }

    @Override
    protected Class[] getExpectedSignatureOutputClasses() {
        return new Class[] { Long.class };
    }

    @Test
    @Override
    public void shouldJsonSerialiseAndDeserialise() throws IOException {
        // Given
        final ParseTime function = new ParseTime().format("yyyy dd").timeUnit("SECOND").timeZone("GMT");

        // When
        final String json = JsonSerialiser.serialise(function);

        // Then
        JsonSerialiser.assertEquals("{\"class\":\"uk.gov.gchq.koryphe.impl.function.ParseTime\",\"format\":\"yyyy dd\",\"timeZone\":\"GMT\",\"timeUnit\":\"SECOND\"}", json);
    }

    @Test
    public void shouldParseTime() throws ParseException {
        // Given
        final ParseTime function = new ParseTime();
        final String input = "2000-01-02 03:04:05.006";

        // When
        long result = function.apply(input);

        // Then
        assertEquals(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").parse(input).getTime(), result);
    }

    @Test
    public void shouldParseTimeWithFormat() throws ParseException {
        // Given
        final ParseTime function = new ParseTime().format("yyyy-MM hh:mm:ss.SSS");
        final String input = "2000-01 03:04:05.006";

        // When
        long result = function.apply(input);

        // Then
        assertEquals(new SimpleDateFormat("yyyy-MM hh:mm:ss.SSS").parse(input).getTime(), result);
    }

    @Test
    public void shouldReturnNullForNullInput() {
        // Given
        final ParseTime function = new ParseTime();

        // When
        final Object result = function.apply(null);

        // Then
        assertNull(result);
    }
}
