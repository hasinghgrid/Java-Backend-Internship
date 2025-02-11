package org.example.search.SearchEngineInterface;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AllSearchStrategy implements SimpleSearchEngine {

    @Override
    public Set<Integer> search(String[] queryWords, Map<String, Set<Integer>> invertedIndex) {
        Set<Integer> resultIndexes = new HashSet<>(invertedIndex.getOrDefault(queryWords[0], Set.of()));
        for (int i = 1; i < queryWords.length; i++) {
            resultIndexes.retainAll(invertedIndex.getOrDefault(queryWords[i], Set.of()));
        }
        return resultIndexes;
    }
}