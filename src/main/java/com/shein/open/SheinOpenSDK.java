package com.shein.open;

import com.shein.open.api.SheinOpenSDKClient;
import com.shein.open.api.SheinOpenSDKClientImpl;
import com.shein.open.config.SheinAppConfig;


/**
 * Get the entrance to sheinOpenSDKClient
 * SheinSDK
 */
public final class SheinOpenSDK {
    /**
     * create SheinOpenApi instance
     */
    private static volatile SheinOpenSDKClient instance;
    private static volatile SheinAppConfig config;

    /**
     * getInstance
     * @param appConfig appConfig
     * @return SheinOpenSDKClient
     * */
    public static SheinOpenSDKClient getInstance(SheinAppConfig appConfig) {
        if (appConfig == null) {
            throw new IllegalArgumentException("Config cannot be null");
        }

        if (instance == null || !appConfig.equals(config)) {
            synchronized (SheinOpenSDK.class) {
                if (instance == null || !appConfig.equals(config)) {
                    instance = new SheinOpenSDKClientImpl(appConfig);
                    config = appConfig;
                }
            }
        }
        return instance;
    }

    private SheinOpenSDK() {
    }
}
