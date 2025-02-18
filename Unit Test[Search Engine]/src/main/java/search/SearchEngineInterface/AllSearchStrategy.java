package search.SearchEngineInterface;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;

public class AllSearchStrategy implements SimpleSearchEngine {

    @Override
    public Set<Integer> search(String[] queryWords, Map<String, Set<Integer>> invertedIndex) {
        // Check if the word is in the invertedIndex and initialize the result set
        Set<Integer> resultIndexes = new HashSet<>(invertedIndex.getOrDefault(queryWords[0], new HashSet<>()));

        // For each query word, retain the elements common with the previous set
        for (int i = 1; i < queryWords.length; i++) {
            resultIndexes.retainAll(invertedIndex.getOrDefault(queryWords[i], new HashSet<>()));
        }
        return resultIndexes;
    }
}
