package com.shein.open.api;

import com.shein.open.exception.OpenSdkException;
import com.shein.open.http.RequestBuilder;
import com.shein.open.model.AuthInfo;

/**
 * SheinOpenApi
 */
public interface SheinOpenSDKClient {
    /**
     * get auth token
     *
     * @param tempToken tempToken
     * @param authInfo authInfo
     * @return token object
     * @throws OpenSdkException If a request fails， throw an exception
     */
    String getToken(String tempToken, AuthInfo authInfo) throws OpenSdkException;

    /**
     * send get request
     *
     * @param requestBuilder Request Builder
     * @return response string
     * @throws OpenSdkException If a request fails， throw an exception
     */
    String get(RequestBuilder requestBuilder) throws OpenSdkException;

    /**
     * send post request
     *
     * @param requestBuilder Request Builder
     * @return response string
     * @throws OpenSdkException If a request fails， throw an exception
     */
    String post(RequestBuilder requestBuilder) throws OpenSdkException;

    /**
     * decrypt SecretKey
     * The secretKey obtained via the get-by-token interface is encrypted and needs to be decrypted before use.
     *
     * @param encryptedData encrypted data
     * @param secretKey secretKey
     * @return SecretKey
     */
    String decryptSecretKey(String encryptedData, String secretKey);

    /**
     * decrypt decryptEventData
     * The data in the webhook callback is encrypted and needs to be decrypted before it can be used.
     *
     * @param encryptedData encrypted data
     * @param secretKey secretKey
     * @return EventData
     */
    String decryptEventData(String encryptedData, String secretKey);
}
