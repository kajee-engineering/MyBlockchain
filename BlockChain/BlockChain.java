package BlockChain;

import java.security.MessageDigest;
import java.util.*;
import java.security.NoSuchAlgorithmException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BlockChain {

    private List<Map<String, Object>> chain;
    private ArrayList<Object> transactionPool;
    private final String blockchainAddress;

    private final Integer MINING_DIFFICULTY = 3;
    private final String MINING_SENDER = "THE BLOCKCHAIN";
    private final double MINING_REWARD = 1.0;

    public BlockChain(String blockchainAddress) {
        this.transactionPool = new ArrayList<>();
        this.chain = new ArrayList<>();
        createBlock(0, this.hash(new TreeMap<>()));
        this.blockchainAddress = blockchainAddress;
    }

    public void createBlock(
            int nonce,
            String previousHash
    ) {
        Map<String, Object> block = new TreeMap<>();
        block.put("timestamp", System.nanoTime());
        block.put("transactions", this.transactionPool);
        block.put("nonce", nonce);
        block.put("hash", previousHash);

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

    public void addTransaction(
            String senderAddress,
            String recipientAddress,
            double Value
    ) {
        Map<String, Object> transaction = new TreeMap<>();
        transaction.put("senderAddress", senderAddress);
        transaction.put("recipientAddress", recipientAddress);
        transaction.put("Value", Value);

        this.transactionPool.add(transaction);
    }

    public boolean validProof(
            ArrayList<Object> transactions,
            Integer nonce,
            String previous_hash
            ) {
        String guess_block = transactions.toString() + nonce.toString() + previous_hash;
        String guess_hash = hash(Collections.singletonMap("guess_block", guess_block));
        return guess_hash.substring(0, MINING_DIFFICULTY).equals("0".repeat(MINING_DIFFICULTY));
    }

    public Integer proofOfWork() {
        ArrayList<Object> transactions = new ArrayList<>(this.transactionPool);
        int lastindex = this.getChain().size() - 1;
        String previous_hash = this.hash(this.getChain().get(lastindex));
        int nonce = 0;
        while (! validProof(transactions, nonce, previous_hash)) {
            nonce++;
        }
        return nonce;

    }

    public void mining() {
        this.addTransaction(
                MINING_SENDER,
                this.blockchainAddress,
                MINING_REWARD
        );
        int nonce;
        nonce = this.proofOfWork();
        int lastIndex = this.getChain().size() - 1;
        String previousHash = this.hash(this.getChain().get(lastIndex));
        this.createBlock(nonce, previousHash);
    }

    public double calculateTotalAmount(String blockchainAddress) {
        double totalAmount = 0;
        for (Map<String, Object> block : this.chain) {
            List<Map<String, Object>> transactions = (List<Map<String, Object>>) block.get("transactions");
            if (transactions != null) {
                for (Map<String, Object> transaction : transactions) {
                    if (transaction.get("senderAddress").equals(blockchainAddress)) {
                        totalAmount -= (double) transaction.get("Value");
                    }
                    if (transaction.get("recipientAddress").equals(blockchainAddress)) {
                        totalAmount += (double) transaction.get("Value");
                    }
                }
            }
        }
        return totalAmount;
    }


    public List<Map<String, Object>> getChain() {
        return chain;
    }
}