package com.shein.open.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shein.open.config.SheinAppConfig;
import com.shein.open.exception.OpenSdkException;
import com.shein.open.model.AuthInfo;
import com.shein.open.model.SignModeEnum;
import com.shein.open.util.Assertions;
import com.shein.open.util.SignUtil;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * HttpClientProxy
 */
public class HttpClientProxy implements AutoCloseable {
    private final Logger log = LoggerFactory.getLogger(HttpClientProxy.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final SheinAppConfig config;
    private final CloseableHttpClient client;

    /**
     * build HttpClientProxy
     *
     * @param config config
     */
    public HttpClientProxy(SheinAppConfig config) {
        this.config = config;
        // create connectionManager
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(config.getMaxConnections()); // Set the maximum number of connections
        connectionManager.setDefaultMaxPerRoute(config.getMaxConnections() / 4); // Maximum number of connections per route

        // Set Request Timeout
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout((int) config.getConnectTimeoutMillis()) // Connection Timeout
                .setSocketTimeout((int) config.getResponseTimeoutMillis()) // Socket Timeout
                .setConnectionRequestTimeout((int) config.getRequestTimeoutMillis()) // Request Timeout
                .build();

        // create HttpClient
        this.client = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    /**
     * doExecute
     *
     * @param requestBuilder requestBuilder
     * @return STRING
     * @throws OpenSdkException openSdkException
     */
    public String doExecute(RequestBuilder requestBuilder) throws OpenSdkException {
        Assertions.notNull(requestBuilder.getAuthInfo(), "AuthInfo must not be null");
        String appid = requestBuilder.getAuthInfo().getAppid();
        String appSecret = requestBuilder.getAuthInfo().getAppSecret();
        String openKeyId = requestBuilder.getAuthInfo().getOpenKeyId();
        String secretKey = requestBuilder.getAuthInfo().getSecretKey();

        Assertions.notBlank(requestBuilder.getMethod(), "The httpMethod must not be blank");
        Assertions.notBlank(config.getDomain(), "The domain must not be blank");

        try {
            String fullUrl = config.getDomain() + requestBuilder.getUrl();
            String timestamp = String.valueOf(System.currentTimeMillis());

            // Handle query parameters
            if (!requestBuilder.getQueryParams().isEmpty()) {
                fullUrl += (fullUrl.contains("?") ? "&" : "?") +
                        requestBuilder.getQueryParams().entrySet().stream()
                                .map(e -> {
                                    try {
                                        return e.getKey() + "=" + URLEncoder.encode(e.getValue(), String.valueOf(StandardCharsets.UTF_8));
                                    } catch (UnsupportedEncodingException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                })
                                .collect(Collectors.joining("&"));
            }
            //signature
            String signature = null;
            if (SignModeEnum.OPEN_KEY_ID.equals(requestBuilder.getAuthInfo().getSignModeEnum())) {
                Assertions.notBlank(openKeyId, "The openKeyId must not be blank");
                Assertions.notBlank(secretKey, "The secretKey must not be blank");
                signature = SignUtil.calculateSignature(openKeyId, secretKey, requestBuilder.getUrl(), timestamp);
            } else {
                Assertions.notBlank(appid, "The appid must not be blank");
                Assertions.notBlank(appSecret, "The appSecret must not be blank");
                signature = SignUtil.calculateSignature(appid, appSecret, requestBuilder.getUrl(), timestamp);
            }

            //Create request instance based on the request method
            switch (requestBuilder.getMethod()) {
                case GET: {
                    HttpGet httpGet = new HttpGet(fullUrl);
                    addHeaders(httpGet, appid, openKeyId, timestamp, signature, requestBuilder);
                    try (CloseableHttpResponse response = client.execute(httpGet)) {
                        return EntityUtils.toString(response.getEntity());
                    }
                }
                case POST: {
                    HttpPost httpPost = new HttpPost(fullUrl);
                    addHeaders(httpPost, appid, openKeyId, timestamp, signature, requestBuilder);
                    if (requestBuilder.getBody() != null) {
                        StringEntity entity = new StringEntity(requestBuilder.getBody(), StandardCharsets.UTF_8);
                        httpPost.setEntity(entity);
                    }
                    try (CloseableHttpResponse response = client.execute(httpPost)) {
                        return EntityUtils.toString(response.getEntity());
                    }
                }
                default:
                    throw new UnsupportedOperationException("Unsupported HTTP method: " + requestBuilder.getMethod());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new OpenSdkException("Failed to perform http request:" + e.getMessage(), e);
        }
    }

    /**
     * build Headers
     *
     * @param request        request
     * @param appid          appid
     * @param openKeyId      openKeyId
     * @param timestamp      timestamp
     * @param signature      signature
     * @param requestBuilder requestBuilder
     */
    private void addHeaders(HttpUriRequest request, String appid, String openKeyId, String timestamp, String signature, RequestBuilder requestBuilder) {
        request.setHeader("x-lt-appid", appid);
        request.setHeader("x-lt-openKeyId", openKeyId);
        request.setHeader("x-lt-timestamp", timestamp);
        request.setHeader("x-lt-signature", signature);
        request.setHeader("Content-Type", "application/json;charset=UTF-8");

        // Add Custom Request Headers
        for (Map.Entry<String, String> entry : requestBuilder.getHeaders().entrySet()) {
            request.setHeader(entry.getKey(), entry.getValue());
        }
    }

    /**
     * getByToken
     *
     * @param tempToken tempToken
     * @param authInfo  authInfo
     * @return token
     * @throws OpenSdkException openSdkException
     */
    public String getByToken(String tempToken, AuthInfo authInfo) throws OpenSdkException {
        Assertions.notNull(authInfo, "AuthInfo must not be null");
        Assertions.notBlank(tempToken, "The tempToken must not be blank");
        Assertions.notBlank(config.getDomain(), "The domain must not be blank");
        Assertions.notBlank(authInfo.getAppid(), "The appid must not be blank");
        Assertions.notBlank(authInfo.getAppSecret(), "The appSecret must not be blank");

        try {
            // Create a JSON body.
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("tempToken", tempToken);
            String requestBody = objectMapper.writeValueAsString(objectNode);
            String timestamp = String.valueOf(System.currentTimeMillis());

            // Create a httpPost
            HttpPost httpPost = new HttpPost(config.getDomain() + "/open-api/auth/get-by-token");
            httpPost.setHeader("x-lt-appid", authInfo.getAppid());
            httpPost.setHeader("x-lt-timestamp", timestamp);
            httpPost.setHeader("x-lt-signature", SignUtil.calculateSignature(authInfo.getAppid(), authInfo.getAppSecret(), "/open-api/auth/get-by-token", timestamp));
            httpPost.setHeader("Content-Type", "application/json");

            // Set body
            StringEntity entity = new StringEntity(requestBody, StandardCharsets.UTF_8);
            httpPost.setEntity(entity);

            // Send Request and Receive Response
            try (CloseableHttpResponse response = client.execute(httpPost)) {
                return EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new OpenSdkException("Failed to perform POST request:" + e.getMessage(), e);
        }
    }

    @Override
    public void close() throws Exception {
        if (client != null) {
            client.close();
        }
    }
}
