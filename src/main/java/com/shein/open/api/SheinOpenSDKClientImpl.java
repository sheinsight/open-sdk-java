package com.shein.open.api;

import com.shein.open.config.SheinAppConfig;
import com.shein.open.exception.OpenSdkException;
import com.shein.open.http.HttpClientProxy;
import com.shein.open.http.RequestBuilder;
import com.shein.open.model.AuthInfo;
import com.shein.open.util.AesUtil;
import com.shein.open.util.Assertions;

/**
 * Note that when changing the class, please make it globally unique and use SheinOpenSDK to obtain it.
 */
public class SheinOpenSDKClientImpl implements SheinOpenSDKClient {

    private final HttpClientProxy httpClient;

    /**
     * SheinOpenSDKClientImpl construct
     * @param config config
     */
    public SheinOpenSDKClientImpl(SheinAppConfig config) {
        this.httpClient = new HttpClientProxy(config);
    }

    @Override
    public String getToken(String tempToken, AuthInfo authInfo) throws OpenSdkException {
        return httpClient.getByToken(tempToken, authInfo);
    }

    @Override
    public String get(RequestBuilder requestBuilder) throws OpenSdkException {
        return httpClient.doExecute(requestBuilder);
    }

    @Override
    public String post(RequestBuilder requestBuilder) throws OpenSdkException {
        return httpClient.doExecute(requestBuilder);
    }

    @Override
    public String decryptSecretKey(String encryptedSecretKey, String appSecret) {
        Assertions.notBlank(encryptedSecretKey, "encryptedSecretKey not be blank");
        Assertions.notBlank(appSecret, "appSecret not be blank");
        return AesUtil.decrypt(encryptedSecretKey, appSecret);
    }

    @Override
    public String decryptEventData(String encryptedEventData, String appSecret) {
        Assertions.notBlank(encryptedEventData, "encryptedEventData not be blank");
        Assertions.notBlank(appSecret, "appSecret not be blank");
        return AesUtil.decrypt(encryptedEventData, appSecret);
    }
}
