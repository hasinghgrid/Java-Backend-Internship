package search.SearchEngineInterfaceTest;

import org.junit.jupiter.api.Test;
import search.SearchEngineInterface.AllSearchStrategy;
import search.SearchEngineInterface.SimpleSearchEngine;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class AllSearchStrategyTest {

    private Map<String, Set<Integer>> getMockInvertedIndex() {
        Map<String, Set<Integer>> invertedIndex = new HashMap<>();
        invertedIndex.put("apple", new HashSet<>(Arrays.asList(1, 2, 3)));
        invertedIndex.put("banana", new HashSet<>(Arrays.asList(2, 3, 4)));
        invertedIndex.put("cherry", new HashSet<>(Arrays.asList(1, 4, 5)));
        return invertedIndex;
    }

    @Test
    public void testAllSearchStrategy() {
        SimpleSearchEngine searchEngine = new AllSearchStrategy();
        Map<String, Set<Integer>> invertedIndex = getMockInvertedIndex();

        // Searching for words "apple" and "banana"
        String[] queryWords = {"apple", "banana"};
        Set<Integer> result = searchEngine.search(queryWords, invertedIndex);

        // Expected result: records that contain both "apple" and "banana"
        Set<Integer> expected = new HashSet<>(Arrays.asList(2, 3));

        assertEquals(expected, result);
    }


    @Test
    public void testSearchNoMatchingWords() {
        SimpleSearchEngine searchEngine = new AllSearchStrategy();
        Map<String, Set<Integer>> invertedIndex = getMockInvertedIndex();

        // Searching for words that do not exist in the inverted index
        String[] queryWords = {"kiwi", "orange"};
        Set<Integer> result = searchEngine.search(queryWords, invertedIndex);

        // Expected result: an empty set, as neither "kiwi" nor "orange" are in the inverted index
        Set<Integer> expected = new HashSet<>();

        assertEquals(expected, result);
    }
}
