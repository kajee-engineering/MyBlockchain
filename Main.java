import BlockChain.BlockChain;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        BlockChain blockchain = new BlockChain();

        blockchain.addTransaction("Alice", "Bob", 100);
        int lastindex = blockchain.getChain().size() - 1;
        String previous_hash = blockchain.hash(blockchain.getChain().get(lastindex));
        Integer nonce = blockchain.proofOfWork();

        blockchain.createBlock(nonce, previous_hash);

        blockchain.addTransaction("Bob", "Alice", 50);
        blockchain.addTransaction("Alice", "Bob", 100);
        int lastindex2 = blockchain.getChain().size() - 1;
        String previous_hash2 = blockchain.hash(blockchain.getChain().get(lastindex2));
        Integer nonce2 = blockchain.proofOfWork();
        blockchain.createBlock(nonce2,  previous_hash2);

        blockchain.pprint(blockchain.getChain());
        }
}
