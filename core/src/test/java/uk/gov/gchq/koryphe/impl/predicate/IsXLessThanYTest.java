package uk.gov.gchq.koryphe.impl.predicate;

import org.junit.jupiter.api.Test;

import uk.gov.gchq.koryphe.predicate.PredicateTest;
import uk.gov.gchq.koryphe.util.JsonSerialiser;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IsXLessThanYTest extends PredicateTest<IsXLessThanY> {

    @Test
    public void shouldAcceptWhenLessThan() {
        // Given
        final IsXLessThanY filter = new IsXLessThanY();

        // When
        boolean accepted = filter.test(1, 2);

        // Then
        assertTrue(accepted);
    }

    @Test
    public void shouldRejectTheValueWhenMoreThan() {
        // Given
        final IsXLessThanY filter = new IsXLessThanY();

        // When
        boolean accepted = filter.test(6, 5);

        // Then
        assertFalse(accepted);
    }

    @Test
    public void shouldRejectTheValueWhenEqualTo() {
        // Given
        final IsXLessThanY filter = new IsXLessThanY();

        // When
        boolean accepted = filter.test(5, 5);

        // Then
        assertFalse(accepted);
    }

    @Test
    public void shouldJsonSerialiseAndDeserialise() throws IOException {
        // Given
        final IsXLessThanY filter = new IsXLessThanY();

        // When
        final String json = JsonSerialiser.serialise(filter);

        // Then
        JsonSerialiser.assertEquals(String.format("{%n" +
                "  \"class\" : \"uk.gov.gchq.koryphe.impl.predicate.IsXLessThanY\"%n" +
                "}"), json);

        // When 2
        final IsXLessThanY deserialisedFilter = JsonSerialiser.deserialise(json, IsXLessThanY.class);

        // Then 2
        assertNotNull(deserialisedFilter);
    }

    @Test
    public void shouldCheckInputClass() {
        // When
        final IsXLessThanY predicate = new IsXLessThanY();

        // Then
        assertTrue(predicate.isInputValid(Integer.class, Integer.class).isValid());
        assertTrue(predicate.isInputValid(String.class, String.class).isValid());

        assertFalse(predicate.isInputValid(Double.class).isValid());
        assertFalse(predicate.isInputValid(Integer.class, Double.class).isValid());
    }

    @Override
    protected IsXLessThanY getInstance() {
        return new IsXLessThanY();
    }

    @Override
    protected Iterable<IsXLessThanY> getDifferentInstancesOrNull() {
        return null;
    }
}
