package com.shein.open.http;

import com.shein.open.model.AuthInfo;
import com.shein.open.model.HttpMethodEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * RequestBuilder
 */
public class RequestBuilder {
    /**
     * the httpMethod which you use
     */
    private HttpMethodEnum method;
    /**
     * api url
     */
    private String url;
    /**
     * http header
     */
    private Map<String, String> headers = new HashMap<>();
    /**
     * get param
     */
    private Map<String, String> queryParams = new HashMap<>();
    /**
     * post jsonString
     */
    private String body;
    /**
     * AuthInfo
     */
    private AuthInfo authInfo;

    /**
     * getMethod
     * @return HttpMethodEnum
     */
    public HttpMethodEnum getMethod() {
        return method;
    }

    /**
     * setMethod
     * @param method method
     */
    public void setMethod(HttpMethodEnum method) {
        this.method = method;
    }

    /**
     * getUrl
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * setUrl
     * @param url url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * getHeaders
     * @return map
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * setHeaders
     * @param headers headers
     */
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers != null ? new HashMap<>(headers) : new HashMap<>();
    }

    /**
     * getQueryParams
     * @return queryParams
     */
    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    /**
     * setQueryParams
     * @param queryParams queryParams
     */
    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams != null ? new HashMap<>(queryParams) : new HashMap<>();
    }

    /**
     * getBody
     * @return body
     */
    public String getBody() {
        return body;
    }

    /**
     * setBody
     * @param body body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * getAuthInfo
     * @return authInfo
     */
    public AuthInfo getAuthInfo() {
        return authInfo;
    }

    /**
     * setAuthInfo
     * @param authInfo authInfo
     */
    public void setAuthInfo(AuthInfo authInfo) {
        this.authInfo = authInfo;
    }
}
