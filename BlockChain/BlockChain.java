package BlockChain;

import java.security.MessageDigest;
import java.util.*;
import java.security.NoSuchAlgorithmException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BlockChain {

    private ArrayList<Object> transactionPool;
    private List<Map<String, Object>> chain;

    public BlockChain() {
        this.transactionPool = new ArrayList<>();
        this.chain = new ArrayList<>();
        createBlock(0, this.hash(new TreeMap<>()));
    }

    public void createBlock(int nonce, String previousHash) {
        Map<String, Object> block = new TreeMap<>();
        block.put("timestamp", System.currentTimeMillis());
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