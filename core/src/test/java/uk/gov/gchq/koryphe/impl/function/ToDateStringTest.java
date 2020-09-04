package uk.gov.gchq.koryphe.impl.function;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.gov.gchq.koryphe.function.FunctionTest;
import uk.gov.gchq.koryphe.util.JsonSerialiser;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class ToDateStringTest extends FunctionTest<ToDateString> {

    private static TimeZone originalTimezone;

    @BeforeAll
    public static void beforeClass() {
        originalTimezone = TimeZone.getDefault();
        TimeZone.setDefault(TimeZone.getTimeZone("UTC")); // For consistency
    }

    @AfterAll
    public static void afterClass() {
        TimeZone.setDefault(originalTimezone);
    }

    @Override
    protected Class[] getExpectedSignatureInputClasses() {
        return new Class[] { Date.class };
    }

    @Override
    protected Class[] getExpectedSignatureOutputClasses() {
        return new Class[] { String.class };
    }

    @Test
    @Override
    public void shouldJsonSerialiseAndDeserialise() throws IOException {
        // Given
        Function instance = getInstance();
        String json = "{\n" +
                "\t\"class\": \"uk.gov.gchq.koryphe.impl.function.ToDateString\",\n" +
                "\t\"format\": \"YYYY-MM-dd HH:mm:ss.SSS\"\n" +
                "}";

        // When
        String serialised = JsonSerialiser.serialise(instance);
        ToDateString deserialised = JsonSerialiser.deserialise(json, ToDateString.class);

        // Then
        JsonSerialiser.assertEquals(json, serialised);
        assertEquals(instance, deserialised);
    }

    @Override
    protected ToDateString getInstance() {
        return new ToDateString("YYYY-MM-dd HH:mm:ss.SSS");
    }

    @Override
    protected Iterable<ToDateString> getDifferentInstancesOrNull() {
        return Arrays.asList(
                new ToDateString(),
                new ToDateString("dd-MMM-yy hh:mm:ss")
        );
    }

    @Test
    public void shouldConvertDateToStringAccordingToFormat() {
        // Given
        ToDateString instance = getInstance();

        // When
        String dateString = instance.apply(Date.from(Instant.EPOCH));

        // Then
        assertEquals("1970-01-01 00:00:00.000", dateString);
    }

    @Test
    public void shouldUseDefaultFormatIfNoneAreGiven() {
        // Given
        ToDateString instance = new ToDateString();

        // When
        String dateString = instance.apply(Date.from(Instant.EPOCH));

        // Then
        assertEquals("1970-01-01T00:00:00Z", dateString);
    }

    @Test
    public void shouldReturnNullIfInputIsNull() {
        // Given
        ToDateString instance = new ToDateString();

        // When
        String dateString = instance.apply(null);

        // Then
        assertNull(dateString);
    }
}