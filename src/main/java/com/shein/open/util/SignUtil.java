package com.shein.open.util;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

/**
 * SignUtil
 */
public class SignUtil {

    private static final String HMAC_SHA256 = "HmacSHA256";
    private static final Integer RANDOM_LENGTH = 5;

    /**
     * calculateSignature
     *
     * @param openKey   openKey
     * @param secretKey secretKey
     * @param apiPath   apiPath
     * @param timestamp timestamp
     * @return string
     */
    public static String calculateSignature(String openKey, String secretKey, String apiPath, String timestamp) {
        String signString = openKey + "&" + timestamp + "&" + apiPath;
        String randomKey = UUID.randomUUID().toString().substring(0, RANDOM_LENGTH);
        String hashValue = hmacSha256(signString, secretKey + randomKey);
        String base64Value = base64Encode(hashValue);
        return randomKey + base64Value;
    }

    private static String hmacSha256(String message, String secret) {
        String hash = "";
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256);
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
            mac.init(secretKey);
            byte[] bytes = mac.doFinal(message.getBytes());
            hash = byteArrayToHexString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hash;
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String tmp;
        for (int n = 0; b != null && n < b.length; n++) {
            tmp = Integer.toHexString(b[n] & 0XFF);
            if (tmp.length() == 1) {
                hs.append('0');
            }
            hs.append(tmp);
        }
        return hs.toString().toLowerCase();
    }

    /**
     * base64Encode
     *
     * @param data data
     * @return String
     */
    public static String base64Encode(String data) {
        String result = "";
        if (data != null && !data.isEmpty()) {
            result = new String(Base64.getEncoder().encode(data.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        }
        return result;
    }

}
