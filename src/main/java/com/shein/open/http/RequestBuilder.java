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

    public HttpMethodEnum getMethod() {
        return method;
    }

    public void setMethod(HttpMethodEnum method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public AuthInfo getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(AuthInfo authInfo) {
        this.authInfo = authInfo;
    }
}
