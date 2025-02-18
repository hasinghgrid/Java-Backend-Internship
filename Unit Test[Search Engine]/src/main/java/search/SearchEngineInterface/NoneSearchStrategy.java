package search.SearchEngineInterface;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NoneSearchStrategy implements SimpleSearchEngine {

    @Override
    public Set<Integer> search(String[] queryWords, Map<String, Set<Integer>> invertedIndex) {
        Set<Integer> allIndexes = new HashSet<>();
        invertedIndex.values().forEach(allIndexes::addAll);

        Set<Integer> resultIndexes = new HashSet<>(allIndexes);
        for (String word : queryWords) {
            // Replace Set.of() with new HashSet<> to support Java 8
            resultIndexes.removeAll(invertedIndex.getOrDefault(word, new HashSet<>()));
        }
        return resultIndexes;
    }
}
