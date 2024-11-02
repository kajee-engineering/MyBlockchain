package BlockChain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlockChain {

    private ArrayList<Object> transactionPool;
    private ArrayList<Object> chain;

    public BlockChain() {
        this.transactionPool = new ArrayList<>();
        this.chain = new ArrayList<>();
        createBlock(0, "init hash");
    }

    public void createBlock(int nonce, String initHash) {
        Map<String, Object> block = new HashMap<>();
        block.put("timestamp", System.currentTimeMillis());
        block.put("transactions", this.transactionPool);
        block.put( "nonce:", nonce);
        block.put( "hash:", initHash);

        chain.add(block);
        transactionPool.clear();
    }

    public ArrayList<Object> getChain() {
        return chain;
    }

    public void pprint(ArrayList chain) {
        for (Object block : chain) {
            System.out.println(block);
        }
    }
}
