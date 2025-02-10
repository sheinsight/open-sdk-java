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
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String DEFAULT_IV_SEED = "space-station-de";

    private AesUtil() {

    }

    /**
     * decrypt
     * @param content content
     * @param key key
     */
    public static String decrypt(String content, String key) {
        return decrypt(content, key, DEFAULT_IV_SEED.getBytes(StandardCharsets.UTF_8), false);
    }

    /**
     * decrypt
     * @param content content
     * @param key key
     * @param iv iv
     * @param useSecureRandom useSecureRandom
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
        }
        return null;
    }

    /**
     * getSecretKey
     * @param randomKey randomKey
     * @param key key
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
     * generateRandomSecretKey
     * @param key key
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
