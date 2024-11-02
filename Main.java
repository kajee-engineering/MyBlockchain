import BlockChain.BlockChain;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        BlockChain blockchain = new BlockChain();
        blockchain.createBlock(5, "hash1");
        blockchain.createBlock(1, "hash2");

        blockchain.pprint(blockchain.getChain());
        }
}
