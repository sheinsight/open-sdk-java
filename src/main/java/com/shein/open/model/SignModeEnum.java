package com.shein.open.model;

/**
 * SignModeEnum
 * There are two signature modes:
 * 1: Application Signature Verification - This uses the appsecretKey for signing. During verification, the HTTP header information must include `x-lt-appid` with the value set to the application's appid.
 * 2: Non-Application Signature Verification - This uses the secretKey obtained after successful authorization for signing. During verification, the HTTP header information must include `x-lt-openKeyId` with the value set to the application's openkeyId.
 * <p>
 * Most of the external APIs currently use non-application signature verification, with only a few APIs requiring application signature verification.
 */

public enum SignModeEnum {

    /**
     * openKeyId signature
     */
    OPEN_KEY_ID("openKeyId signature"),
    /**
     * appid signature
     */
    APPID("appid signature"),
    ;

    private final String desc;

    SignModeEnum(String desc) {
        this.desc = desc;
    }

    /**
     * getDesc
     * @return string
     */
    public String getDesc() {
        return desc;
    }
}
