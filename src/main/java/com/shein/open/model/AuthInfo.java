package com.shein.open.model;

/**
 * @Description: AuthInfo
 * @author: Murphy Liu
 * @since: 2024-10-23 22:21
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

    public static AuthInfo buildAuthInfo(SignModeEnum signModeEnum) {
        AuthInfo authInfo = new AuthInfo();
        authInfo.signModeEnum = signModeEnum;
        return authInfo;
    }

    public AuthInfo withAppid(String appid) {
        this.appid = appid;
        return this;
    }

    public AuthInfo withAppSecret(String appSecret) {
        this.appSecret = appSecret;
        return this;
    }

    public AuthInfo withOpenKeyId(String openKeyId) {
        this.openKeyId = openKeyId;
        return this;
    }

    public AuthInfo withSecretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    public SignModeEnum getSignModeEnum() {
        return signModeEnum;
    }

    public String getAppid() {
        return appid;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getOpenKeyId() {
        return openKeyId;
    }

    public String getSecretKey() {
        return secretKey;
    }
}
