package search.SearchEngineInterface;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AnySearchStrategy implements SimpleSearchEngine {

    @Override
    public Set<Integer> search(String[] queryWords, Map<String, Set<Integer>> invertedIndex) {
        Set<Integer> resultIndexes = new HashSet<>();
        for (String word : queryWords) {
            // Use new HashSet<>() instead of Set.of() for compatibility with Java 8
            resultIndexes.addAll(invertedIndex.getOrDefault(word, new HashSet<>()));
        }
        return resultIndexes;
    }
}
