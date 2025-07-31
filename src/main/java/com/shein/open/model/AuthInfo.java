package com.shein.open.model;

/**
 * AuthInfo
 * Murphy Liu
 * 2024-10-23 22:21
 **/
public class AuthInfo {
    private SignModeEnum signModeEnum;
    /**
     * appid
     */
    private String appid;
    /**
     * appSecret
     */
    private String appSecret;
    /**
     * openKeyId is obtained through the get-by-token interface.
     */
    private String openKeyId;
    /**
     * secretKey is obtained through the get-by-token interface.
     */
    private String secretKey;

    private AuthInfo() {

    }

    /**
     * buildAuthInfo
     * @param signModeEnum signModeEnum
     * @return AuthInfo
     */
    public static AuthInfo buildAuthInfo(SignModeEnum signModeEnum) {
        AuthInfo authInfo = new AuthInfo();
        authInfo.signModeEnum = signModeEnum;
        return authInfo;
    }

    /**
     * withAppid
     * @param appid appid
     * @return AuthInfo
     */
    public AuthInfo withAppid(String appid) {
        this.appid = appid;
        return this;
    }

    /**
     * withAppSecret
     * @param appSecret appSecret
     * @return AuthInfo
     */
    public AuthInfo withAppSecret(String appSecret) {
        this.appSecret = appSecret;
        return this;
    }

    /**
     * withOpenKeyId
     * @param openKeyId openKeyId
     * @return AuthInfo
     */
    public AuthInfo withOpenKeyId(String openKeyId) {
        this.openKeyId = openKeyId;
        return this;
    }

    /**
     * withSecretKey
     * @param secretKey secretKey
     * @return AuthInfo
     */
    public AuthInfo withSecretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    /**
     * getSignModeEnum
     * @return SignModeEnum
     */
    public SignModeEnum getSignModeEnum() {
        return signModeEnum;
    }

    /**
     * getAppid
     * @return appid
     */
    public String getAppid() {
        return appid;
    }

    /**
     * getAppSecret
     * @return appSecret
     */
    public String getAppSecret() {
        return appSecret;
    }

    /**
     * getOpenKeyId
     * @return openKeyId
     */
    public String getOpenKeyId() {
        return openKeyId;
    }

    /**
     * getSecretKey
     * @return secretKey
     */
    public String getSecretKey() {
        return secretKey;
    }
}
