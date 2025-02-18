package search.SearchEngineInterfaceTest;

import org.junit.jupiter.api.Test;
import search.SearchEngineInterface.NoneSearchStrategy;
import search.SearchEngineInterface.SimpleSearchEngine;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class NoneSearchStrategyTest {

    private Map<String, Set<Integer>> getMockInvertedIndex() {
        Map<String, Set<Integer>> invertedIndex = new HashMap<>();
        invertedIndex.put("apple", new HashSet<>(Arrays.asList(1, 2, 3)));
        invertedIndex.put("banana", new HashSet<>(Arrays.asList(2, 3, 4)));
        invertedIndex.put("cherry", new HashSet<>(Arrays.asList(1, 4, 5)));
        return invertedIndex;
    }

    @Test
    public void testNoneSearchStrategy() {
        SimpleSearchEngine searchEngine = new NoneSearchStrategy();
        Map<String, Set<Integer>> invertedIndex = getMockInvertedIndex();

        // Searching for words "apple" and "banana"
        String[] queryWords = {"apple", "banana"};
        Set<Integer> result = searchEngine.search(queryWords, invertedIndex);

        // Expected result: records that do not contain either "apple" or "banana"
        Set<Integer> expected = new HashSet<>(Arrays.asList(5)); // Only cherry (ID 5) is not in apple or banana

        assertEquals(expected, result);
    }

    @Test
    public void testSearchEmptyQuery() {
        SimpleSearchEngine searchEngine = new NoneSearchStrategy();
        Map<String, Set<Integer>> invertedIndex = getMockInvertedIndex();

        // Searching with an empty query should return all records
        String[] queryWords = {};
        Set<Integer> result = searchEngine.search(queryWords, invertedIndex);

        // Expected result: all records (since the query is empty)
        Set<Integer> expected = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));

        assertEquals(expected, result);
    }

    @Test
    public void testSearchNoMatchingWords() {
        SimpleSearchEngine searchEngine = new NoneSearchStrategy();
        Map<String, Set<Integer>> invertedIndex = getMockInvertedIndex();

        // Searching for words that do not exist in the inverted index
        String[] queryWords = {"kiwi", "orange"};
        Set<Integer> result = searchEngine.search(queryWords, invertedIndex);

        // Expected result: all records (1, 2, 3, 4, 5) because no matches were found
        Set<Integer> expected = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));

        assertEquals(expected, result);
    }

}
