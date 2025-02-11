package org.example.search.SearchEngineInterface;

import java.util.Map;
import java.util.Set;

public interface SimpleSearchEngine {
    public Set<Integer> search(String[] queryWords, Map<String, Set<Integer>> invertedIndex);

}

