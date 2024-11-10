package BlockChain;

import java.util.List;
import java.util.Map;

public class Util {
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
