package com.shein.open.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * AesUtil
 */
public class AesUtil {

    private static final Logger log = Logger.getLogger(AesUtil.class.getName());

    private static final String UTF_8 = "utf-8";
    private static final String KEY_ALGORITHM = "AES";
    private static final int BLOCK_LENGTH = 128;
    private static final Integer IV_LENGTH = 16;
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String DEFAULT_IV_SEED = "space-station-de";

    private AesUtil() {

    }

    /**
     * Decrypt data using default IV
     * Uses a predefined IV seed for decryption. This method is suitable for
     * decrypting data that was encrypted with the same default IV.
     *
     * @param content Base64 encoded encrypted content
     * @param key     decryption key
     * @return decrypted string, or null if decryption fails
     */
    public static String decrypt(String content, String key) {
        return decrypt(content, key, DEFAULT_IV_SEED.getBytes(StandardCharsets.UTF_8), false);
    }

    /**
     * Decrypt response data with dynamic IV extraction
     * This method is specifically designed for decrypting SHEIN gateway response data.
     * The encrypted content contains the IV (Initialization Vector) in the first 16 bytes,
     * followed by the actual encrypted data.
     *
     * @param content Base64 encoded encrypted content (IV + encrypted data)
     * @param key     decryption key
     * @return decrypted response string
     * @throws IllegalArgumentException if content or key is null/empty, or if ciphertext format is invalid
     */
    public static String decryptResponse(String content, String key) {
        if (content == null || content.isEmpty() || key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Ciphertext and key cannot be empty");
        }
        int ivLength = IV_LENGTH;
        byte[] decode = Base64.getDecoder().decode(content);
        if (decode.length <= ivLength) {
            throw new IllegalArgumentException("Ciphertext Error");
        } else {
            byte[] ivBytes = new byte[ivLength];
            byte[] realData = new byte[decode.length - ivLength];
            System.arraycopy(decode, 0, ivBytes, 0, ivLength);
            System.arraycopy(decode, ivLength, realData, 0, decode.length - ivLength);
            return decrypt(Base64.getEncoder().encodeToString(realData), key, ivBytes);
        }
    }

    private static String decrypt(String content, String key, byte[] ivBytes) {
        return decrypt(content, key, ivBytes, false);
    }

    /**
     * Decrypt data with custom IV and key generation options
     * Core decryption method that supports both deterministic and secure random key generation.
     *
     * @param content         Base64 encoded encrypted content
     * @param key             decryption key
     * @param iv              initialization vector for CBC mode
     * @param useSecureRandom whether to use secure random for key generation (false = deterministic)
     * @return decrypted string, or null if decryption fails
     */
    public static String decrypt(String content, String key, byte[] iv, boolean useSecureRandom) {
        if (content == null || content.isEmpty() || key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Ciphertext and key cannot be empty");
        }
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key, useSecureRandom), ivSpec);
            byte[] result = cipher.doFinal(Base64.getDecoder().decode(content));
            log.log(Level.INFO, "Decryption completed");
            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            log.log(Level.WARNING, "AES decryption failed", ex);
            throw new RuntimeException("AES decryption failed: " + ex.getMessage(), ex);
        }
    }

    /**
     * Generate AES secret key
     * Creates a SecretKeySpec for AES encryption/decryption with two generation modes.
     *
     * @param key       the source key string
     * @param randomKey true for secure random generation, false for deterministic generation
     * @return SecretKeySpec for AES operations
     * @throws RuntimeException if key generation fails
     */
    private static SecretKeySpec getSecretKey(final String key, boolean randomKey) {
        try {
            if (randomKey) {
                return generateRandomSecretKey(key);
            } else {
                return new SecretKeySpec(Arrays.copyOf(key.getBytes(UTF_8), 16), KEY_ALGORITHM);
            }
        } catch (Exception ex) {
            log.log(Level.WARNING, "AES key generation failed", ex);
            throw new RuntimeException("AES key generation failed", ex);
        }
    }

    /**
     * Generate secure random AES secret key
     * Uses SHA1PRNG with key-based seeding to generate a cryptographically secure key.
     * This provides better security than deterministic key derivation.
     *
     * @param key source key string used for seeding the random generator
     * @return SecretKeySpec generated using secure random
     * @throws Exception if key generation fails
     */
    private static SecretKeySpec generateRandomSecretKey(String key) throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(key.getBytes());
        kg.init(BLOCK_LENGTH, secureRandom);
        SecretKey secretKey = kg.generateKey();
        return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
    }
}
