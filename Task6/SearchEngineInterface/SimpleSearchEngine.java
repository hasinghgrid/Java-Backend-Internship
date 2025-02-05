package search.SerachEngineInterface;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public interface SimpleSearchEngine {
    public Set<Integer> search(String[] queryWords, Map<String, Set<Integer>> invertedIndex);

}

