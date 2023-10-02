package xyz.sorridi.stone.common.utils.math;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Ciphering
{
    private final String algorithm;
    private final Cipher encryptCipher, decryptCipher;

    public Ciphering(String algorithm, String padding) throws NoSuchPaddingException, NoSuchAlgorithmException
    {
        this.algorithm = algorithm;
        this.encryptCipher = Cipher.getInstance(padding);
        this.decryptCipher = Cipher.getInstance(padding);
    }

    public byte[] encrypt(byte[] input, String secretKey) throws InvalidKeyException, IllegalBlockSizeException,
                                                                 BadPaddingException
    {
        SecretKey key = generateKey(secretKey);
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        return encryptCipher.doFinal(input);
    }

    public byte[] decrypt(byte[] encryptedBytes, String secretKey) throws InvalidKeyException,
                                                                          IllegalBlockSizeException, BadPaddingException
    {
        SecretKey key = generateKey(secretKey);
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
        return decryptCipher.doFinal(encryptedBytes);
    }

    public String encryptString(String input, String secretKey) throws IllegalBlockSizeException, BadPaddingException,
                                                                       InvalidKeyException
    {
        byte[] encryptedBytes = encrypt(input.getBytes(), secretKey);
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decryptString(String encryptedText, String secretKey) throws IllegalBlockSizeException,
                                                                               BadPaddingException, InvalidKeyException
    {
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = decrypt(encryptedBytes, secretKey);
        return new String(decryptedBytes);
    }

    private SecretKey generateKey(String secretKey)
    {
        return new SecretKeySpec(secretKey.getBytes(), algorithm);
    }

}
