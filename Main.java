import BlockChain.BlockChain;
import BlockChain.util;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        BlockChain blockchain = new BlockChain();
        blockchain.createBlock(5, "hash1");
        blockchain.createBlock(1, "hash2");

        // blockchain.pprint(util.sortedCollectionByKey(blockchain.getChain()));
        Map<String, Object> result = blockchain.getChain().get(1);
        System.out.println("result: " + blockchain.hash(result));
        Map<String, Object> result2= blockchain.getChain().get(2);
        System.out.println("result2: " + blockchain.hash(result2));
        }
}
