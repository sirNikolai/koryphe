package uk.gov.gchq.koryphe.impl.binaryoperator;

import org.junit.jupiter.api.Test;

import uk.gov.gchq.koryphe.binaryoperator.BinaryOperatorTest;
import uk.gov.gchq.koryphe.util.JsonSerialiser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CollectionConcatTest extends BinaryOperatorTest<CollectionConcat> {

    @Test
    public void shouldConcatArraysTogether() {
        // Given
        final CollectionConcat<Object> aggregator = new CollectionConcat<>();

        final ArrayList<Object> list1 = new ArrayList<>(Arrays.asList(1, 2, 3));
        final ArrayList<Object> list2 = new ArrayList<>(Arrays.asList("3", "4", 5L));

        // When
        final Collection<Object> result = aggregator.apply(list1, list2);

        // Then
        assertEquals(Arrays.asList(1, 2, 3, "3", "4", 5L), result);
    }

    @Test
    public void shouldAggregateTreeSetsTogether() {
        // Given
        final TreeSet<String> treeSet1 = new TreeSet<>();
        treeSet1.add("string1");

        final TreeSet<String> treeSet2 = new TreeSet<>();
        treeSet2.add("string3");
        treeSet2.add("string2");

        final CollectionConcat<String> aggregator = new CollectionConcat<>();

        // When
        final Collection<String> result = aggregator.apply(treeSet1, treeSet2);

        // Then
        final TreeSet<String> expectedResult = new TreeSet<>();
        expectedResult.add("string1");
        expectedResult.add("string2");
        expectedResult.add("string3");
        assertEquals(expectedResult, result);
        assertEquals(TreeSet.class, result.getClass());
    }

    @Test
    public void shouldAggregateHashSetsTogether() {
        // Given
        final HashSet<Integer> hashSet1 = new HashSet<>();
        hashSet1.add(1);

        final HashSet<Integer> hashSet2 = new HashSet<>();
        hashSet2.add(2);
        hashSet2.add(3);

        CollectionConcat<Integer> aggregator = new CollectionConcat<>();

        // When
        final Collection<Integer> result = aggregator.apply(hashSet1, hashSet2);

        // Then
        final HashSet<Integer> expectedResult = new HashSet<>();
        expectedResult.add(1);
        expectedResult.add(2);
        expectedResult.add(3);
        assertEquals(expectedResult, result);
        assertEquals(HashSet.class, result.getClass());
    }

    @Test
    public void shouldJsonSerialiseAndDeserialise() throws IOException {
        // Given
        final CollectionConcat aggregator = new CollectionConcat();

        // When 1
        new JsonSerialiser();
        final String json = JsonSerialiser.serialise(aggregator);

        // Then 1
        JsonSerialiser.assertEquals(String.format("{%n" +
                "  \"class\" : \"uk.gov.gchq.koryphe.impl.binaryoperator.CollectionConcat\"%n" +
                "}"), json);

        // When 2
        final CollectionConcat deserialisedAggregator = JsonSerialiser.deserialise(json, CollectionConcat.class);

        // Then 2
        assertNotNull(deserialisedAggregator);
    }

    @Override
    protected CollectionConcat getInstance() {
        return new CollectionConcat();
    }

    @Override
    protected Iterable<CollectionConcat> getDifferentInstancesOrNull() {
        return null;
    }
}
