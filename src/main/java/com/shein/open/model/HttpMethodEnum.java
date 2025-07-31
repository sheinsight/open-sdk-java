package com.shein.open.model;

/**
 * HttpMethodEnum
 */

public enum HttpMethodEnum {

    /**
     * get request
     */
    GET("get request"),
    /**
     * post request
     */
    POST("post request");

    private final String desc;

    HttpMethodEnum(String desc) {
        this.desc = desc;
    }

    /**
     * getDesc
     *
     * @return string
     */
    public String getDesc() {
        return desc;
    }
}
