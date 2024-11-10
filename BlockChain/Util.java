package BlockChain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Util {
    // TreeMapを使ってソートされたコレクションを返しているため不要になった関数
    public static List<Map<String, Object>> sortedCollectionByKey(List<Map<String, Object>> unsortedCollection) {
       List<Map<String, Object>> sortedCollection = new ArrayList<>();

       for (Map<String, Object> targetMap: unsortedCollection) {
           Map<String, Object> sortedMap = new TreeMap<>(targetMap);
           sortedCollection.add(sortedMap);
       }

       return sortedCollection;
    }

    public static void pprint(List<Map<String, Object>> chain) {
        int blockindex = 0;
        for (Map<String, Object> block : chain) {
            System.out.println("========  Chain " + blockindex + "======================== ");
            for (Map.Entry<String, Object> items : block.entrySet()) {
                if (items.getKey().equals("transactions")) {
                    System.out.println(items.getKey());
                    List<Map<String, Object>> transactions = (List<Map<String, Object>>) items.getValue();
                    for (Map<String, Object> transaction: transactions) {
                        System.out.println("-------------------------- ");
                        for (Map.Entry<String, Object> transactionEntry : transaction.entrySet()) {
                            System.out.println(transactionEntry.getKey() + ": " + transactionEntry.getValue());
                        }
                    }
                } else {
                    System.out.println(items.getKey() + ": " + items.getValue());
                }
            }
            System.out.println("---------------------");
            blockindex++;
        }
    }
}
