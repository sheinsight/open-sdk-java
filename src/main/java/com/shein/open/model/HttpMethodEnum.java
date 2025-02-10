package com.shein.open.model;

/**
 * HttpMethodEnum
 */

public enum HttpMethodEnum {

    GET("get request"),
    POST("post request");

    private final String desc;

    HttpMethodEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
