package org.example.search.SearchEngineInterface;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AnySearchStrategy implements SimpleSearchEngine {

    @Override
    public Set<Integer> search(String[] queryWords, Map<String, Set<Integer>> invertedIndex) {
        Set<Integer> resultIndexes = new HashSet<>();
        for (String word : queryWords) {
            resultIndexes.addAll(invertedIndex.getOrDefault(word, Set.of()));
        }
        return resultIndexes;
    }
}