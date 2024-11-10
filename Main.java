import BlockChain.BlockChain;
import BlockChain.Util;
import BlockChain.Wallet;

public class Main {
    public static void main(String[] args) throws Exception {
        BlockChain blockchain = new BlockChain("my blockchain address");

        blockchain.addTransaction("Alice", "Bob", 100); // 大福帳に記録される
        blockchain.mining();

        blockchain.addTransaction("Bob", "Alice", 50); // 大福帳に記録される
        blockchain.addTransaction("Alice", "Bob", 100); // 大福帳に記録される
        blockchain.mining();

        Util.pprint(blockchain.getChain()); // 大福帳の内容を表示する

        System.out.println(blockchain.calculateTotalAmount("my blockchain address"));

       Wallet result = new Wallet();
       System.out.println(result.getPrivateKey());
        System.out.println(result.getPublicKey());



        }
}
