package BlockChain;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.security.NoSuchAlgorithmException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class BlockChain {

    private ArrayList<Object> transactionPool;
    private List<Map<String,Object>> chain;

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

    public String hash(Map<String, Object> block)
    {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(block);

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(jsonString.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b: hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> getChain() {
        return chain;
    }

    public void pprint(List<Map<String, Object>> chain) {
        for (Map<String, Object> block : chain) {
            for (Map.Entry<String, Object> entry : block.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
            System.out.println("---------------------");
        }
    }
}
