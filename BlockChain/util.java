package BlockChain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class util {
    public static List<Map<String, Object>> sortedCollectionByKey(List<Map<String, Object>> unsortedCollection) {
       List<Map<String, Object>> sortedCollection = new ArrayList<>();

       for (Map<String, Object> targetMap: unsortedCollection) {
           Map<String, Object> sortedMap = new TreeMap<>(targetMap);
           sortedCollection.add(sortedMap);
       }

       return sortedCollection;
    }
}
