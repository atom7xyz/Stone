package xyz.sorridi.stone.common.utils.math;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Ciphering utils.
 *
 * @author Sorridi
 * @since 1.0
 */
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

    /**
     * Encrypts the given input with the given secret key.
     *
     * @param input     The input to encrypt.
     * @param secretKey The secret key to use.
     * @return The encrypted input.
     */
    public byte[] encrypt(byte[] input, String secretKey) throws InvalidKeyException, IllegalBlockSizeException,
                                                                 BadPaddingException
    {
        SecretKey key = generateKey(secretKey);
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        return encryptCipher.doFinal(input);
    }

    /**
     * Decrypts the given encrypted bytes with the given secret key.
     *
     * @param encryptedBytes The encrypted bytes to decrypt.
     * @param secretKey      The secret key to use.
     * @return The decrypted bytes.
     */
    public byte[] decrypt(byte[] encryptedBytes, String secretKey) throws InvalidKeyException,
                                                                          IllegalBlockSizeException, BadPaddingException
    {
        SecretKey key = generateKey(secretKey);
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
        return decryptCipher.doFinal(encryptedBytes);
    }

    /**
     * Encrypts the given input with the given secret key.
     *
     * @param input     The input to encrypt.
     * @param secretKey The secret key to use.
     * @return The encrypted input.
     */
    public String encryptString(String input, String secretKey) throws IllegalBlockSizeException, BadPaddingException,
                                                                       InvalidKeyException
    {
        byte[] encryptedBytes = encrypt(input.getBytes(), secretKey);
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypts the given encrypted text with the given secret key.
     *
     * @param encryptedText The encrypted text to decrypt.
     * @param secretKey     The secret key to use.
     * @return The decrypted text.
     */
    public String decryptString(String encryptedText, String secretKey) throws IllegalBlockSizeException,
                                                                               BadPaddingException, InvalidKeyException
    {
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = decrypt(encryptedBytes, secretKey);
        return new String(decryptedBytes);
    }

    /**
     * Generates a secret key from the given string.
     *
     * @param secretKey The secret key to use.
     * @return The generated secret key.
     */
    private SecretKey generateKey(String secretKey)
    {
        return new SecretKeySpec(secretKey.getBytes(), algorithm);
    }

}
