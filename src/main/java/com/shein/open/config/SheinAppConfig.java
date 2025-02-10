package com.shein.open.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Objects;

/**
 * SheinAppConfig
 */

@ConfigurationProperties("shein.open.sdk")
public class SheinAppConfig {
    /**
     * Open platform domain address
     */
    private String domain;

    /**
     * Connect timeout duration for HttpClient
     */
    private long connectTimeoutMillis = 10000;

    /**
     * Connect timeout duration for HttpClient
     */
    private long requestTimeoutMillis = 10000;

    /**
     * Response timeout duration for HttpClient
     */
    private long responseTimeoutMillis = 10000;

    /**
     * Whether to automatically handle redirects for HttpClient
     */
    private boolean followRedirects = true;

    /**
     * maxConnections for HttpClient
     */
    private int maxConnections = 32;


    /**
     * getDomain
     * @return string
     */
    public String getDomain() {
        return domain;
    }

    /**
     * setDomain
     * @param domain domain
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * getConnectTimeoutMillis
     * @return long
     */
    public long getConnectTimeoutMillis() {
        return connectTimeoutMillis;
    }

    /**
     * setConnectTimeoutMillis
     * @param connectTimeoutMillis connectTimeoutMillis
     */
    public void setConnectTimeoutMillis(long connectTimeoutMillis) {
        this.connectTimeoutMillis = connectTimeoutMillis;
    }

    /**
     * getRequestTimeoutMillis
     * @return long
     */
    public long getRequestTimeoutMillis() {
        return requestTimeoutMillis;
    }

    /**
     * setRequestTimeoutMillis
     * @param requestTimeoutMillis requestTimeoutMillis
     */
    public void setRequestTimeoutMillis(long requestTimeoutMillis) {
        this.requestTimeoutMillis = requestTimeoutMillis;
    }

    /**
     * getResponseTimeoutMillis
     * @return long
     */
    public long getResponseTimeoutMillis() {
        return responseTimeoutMillis;
    }

    /**
     * setResponseTimeoutMillis
     * @param responseTimeoutMillis responseTimeoutMillis
     */
    public void setResponseTimeoutMillis(long responseTimeoutMillis) {
        this.responseTimeoutMillis = responseTimeoutMillis;
    }

    /**
     * isFollowRedirects
     * @return boolean
     */
    public boolean isFollowRedirects() {
        return followRedirects;
    }

    /**
     * setFollowRedirects
     * @param followRedirects boolean
     */
    public void setFollowRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
    }

    /**
     * getMaxConnections
     * @return int
     */
    public int getMaxConnections() {
        return maxConnections;
    }

    /**
     * setMaxConnections
     * @param maxConnections maxConnections
     */
    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SheinAppConfig that = (SheinAppConfig) o;
        return connectTimeoutMillis == that.connectTimeoutMillis && requestTimeoutMillis == that.requestTimeoutMillis && responseTimeoutMillis == that.responseTimeoutMillis && followRedirects == that.followRedirects && maxConnections == that.maxConnections && Objects.equals(domain, that.domain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(domain, connectTimeoutMillis, requestTimeoutMillis, responseTimeoutMillis, followRedirects, maxConnections);
    }
}
