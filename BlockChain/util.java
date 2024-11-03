package BlockChain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class util {
    // public static coolection sortedCollectionByKey(unsortedCollection)
    // アイテムの0番目(ex. nance, timestamp)のキーを使ってコレクションをソートする
    // ブロッックのハッシュをとるために、中身をソートしないとハッシュが変わってしまう
    public static List<Map<String, Object>> sortedCollectionByKey(List<Map<String, Object>> unsortedCollection) {
       List<Map<String, Object>> sortedCollection = new ArrayList<>();

       for (Map<String, Object> targetMap: unsortedCollection) {
           Map<String, Object> sortedMap = new TreeMap<>(targetMap); // targetMapをコピーして、そのキーの自然順序付けに従ってソートする
           sortedCollection.add(sortedMap);
       }

       return sortedCollection;
    }
}
