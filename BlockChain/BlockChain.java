package BlockChain;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.util.*;
import java.security.NoSuchAlgorithmException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BlockChain {

    private ArrayList<Object> transactionPool;
    private List<Map<String, Object>> chain;
    private final int mining_difficulty = 3;

    public BlockChain() {
        this.transactionPool = new ArrayList<>();
        this.chain = new ArrayList<>();
        createBlock(0, this.hash(new TreeMap<>()));
    }

    public void createBlock(
            int nonce,
            String previousHash
    ) {
        Map<String, Object> block = new TreeMap<>();
        block.put("timestamp", System.nanoTime());
        block.put("transactions", this.transactionPool);
        block.put("nonce:", nonce);
        block.put("hash:", previousHash);

        chain.add(block);
        this.transactionPool = new ArrayList<>();
    }

    public String hash(Map<String, Object> block) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(block);

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(jsonString.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addTransaction(
            String senderAddress,
            String recipientAddress,
            int Value
    ) {
        Map<String, Object> transaction = new TreeMap<>();
        transaction.put("senderAddress", senderAddress);
        transaction.put("recipientAddress", recipientAddress);
        transaction.put("Value", Value);

        this.transactionPool.add(transaction);

        return true;
    }

    // valid_poof を実装する
    // mining_difficulty の数だけ、先頭に 0 が並んでいるかどうかを確認する
    // transactions, nonce, previous_hash の配列を、hash を作成する
    // hash の先頭が、mining_difficulty の数だけ 0 が並んでいるかどうかを確認する
    // 並んでいたら true  並んでいなかったら false を返す
    public boolean valid_proof(
            ArrayList<Object> transactions,
            Integer nonce,
            String previous_hash
            ) {
        String guess_block = transactions.toString() + nonce.toString() + previous_hash;
        String guess_hash = hash(Collections.singletonMap("guess_block", guess_block));
        return guess_hash.substring(0, mining_difficulty).equals("0".repeat(mining_difficulty));
    }

    // TODO proof of work を実装する
    // return nonce
    // nonce は create_block の引数に渡す。パスワードのようなもの
    // transactionPool には、transaction が入っている
    // chain から最後の block を取得し、その block を hash 化する
    // valid_proof が true になるまで、nonce をインクリメントする
    // true になったら、nonce を返す
    public Integer proofOfWork() {
        ArrayList<Object> transactions = new ArrayList<>(this.transactionPool);
        int lastindex = this.getChain().size() - 1;
        String previous_hash = this.hash(this.getChain().get(lastindex));
        int nonce = 0;
        while (! valid_proof(transactions, nonce, previous_hash)) {
            nonce++;
        }
        return nonce;

    }


















    public List<Map<String, Object>> getChain() {
        return chain;
    }

    public void pprint(List<Map<String, Object>> chain) {
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