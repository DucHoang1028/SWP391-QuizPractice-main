package Utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtils {

    //// Need 16,24,32 \byte or length because of requirement of AES algorithm
    public String secretKeyString = "SWP391Group41234";

    public String EncodeMd5(String input) {
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    //// Get secret key object from string -> to encode or decode
    public SecretKey GetSecretKey(String secret) throws Exception {
        byte[] key = secret.getBytes("UTF-8");
        return new SecretKeySpec(key, 0, key.length, "AES");
    }

    /// base64 with AES alogrithm
    public String EncodeBase64(String input) throws Exception {
        SecretKey secretKey = GetSecretKey(secretKeyString);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(input.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedBytes);

    }

    public String DecodeBase64(String input) throws Exception {
         SecretKey secretKey = GetSecretKey(secretKeyString);
         
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(input);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

}
