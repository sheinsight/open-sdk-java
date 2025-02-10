package com.shein.open.config;

import com.shein.open.SheinOpenSDK;
import com.shein.open.api.SheinOpenSDKClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SheinSdkAutoConfiguration
 */
@Configuration
@EnableConfigurationProperties(SheinAppConfig.class)
public class SheinSdkAutoConfiguration {
    @Bean
    public SheinOpenSDKClient sheinOpenSDKClient(SheinAppConfig config) {
        return SheinOpenSDK.getInstance(config);
    }
}
