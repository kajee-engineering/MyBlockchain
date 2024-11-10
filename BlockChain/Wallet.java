package BlockChain;

import org.bouncycastle.jce.provider.BouncyCastleProvider; //  アルゴリズムを提供するクラス

import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class Wallet {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public Wallet() throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC"); // BC は BouncyCastleProvider の略
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1");
        keyPairGenerator.initialize(ecSpec);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }

    // privateKey は byte[] 型なので、16進数の文字列に変換して返さないと null が返ってしまう
    public String getPrivateKey() {
        return bytesToHex(privateKey.getEncoded());
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append("0");
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
