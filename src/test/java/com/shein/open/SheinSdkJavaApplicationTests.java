package com.shein.open;

import com.shein.open.api.SheinOpenSDKClient;
import com.shein.open.config.SheinAppConfig;
import com.shein.open.exception.OpenSdkException;
import com.shein.open.http.RequestBuilder;
import com.shein.open.model.AuthInfo;
import com.shein.open.model.HttpMethodEnum;
import com.shein.open.model.SignModeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class SheinSdkJavaApplicationTests {
    private SheinOpenSDKClient api;
    private SheinAppConfig config;

    @BeforeEach
    void setUp() {
        config = new SheinAppConfig();
        // 🚨 WARNING: This is a TEST domain, do NOT use it in production! 🚨
        config.setDomain("http://openapi-test01.sheincorp.cn");
        api = SheinOpenSDK.getInstance(config);
    }

    @Test
    void getByToken() throws OpenSdkException {
        // 🚨 WARNING: This is a TEST tempToken, do NOT use it in production! 🚨
        String tempToken = "de723b1c-210b-4ecc-8da4-5f1da6ea0a9b";
        // 🚨 WARNING: This is a TEST key, do NOT use it in production! 🚨
        AuthInfo authInfo = AuthInfo.buildAuthInfo(SignModeEnum.APPID).withAppid("11795D14130008FA5B4FF15DFCADB").withAppSecret("CD220D80B31E48A69FEC0FB6D7223421");
        String response = api.getToken(tempToken, authInfo);
        System.out.println(response);
    }


    @Test
    void goodsList() throws OpenSdkException {
        RequestBuilder requestBuilder = new RequestBuilder();
        requestBuilder.setUrl("/open-api/openapi-business-backend/product/query");
        requestBuilder.setMethod(HttpMethodEnum.POST);
        requestBuilder.setBody("{\"pageNum\":\"1\",\"pageSize\":50}");
        // 🚨 WARNING: This is a TEST key, do NOT use it in production! 🚨
        AuthInfo authInfo = AuthInfo.buildAuthInfo(SignModeEnum.OPEN_KEY_ID).withAppid("11795D14130008FA5B4FF15DFCADB").withOpenKeyId("5C83782096BA46008D66C424CB39803F").withAppSecret("CD220D80B31E48A69FEC0FB6D7223421").withSecretKey("283598F3DEA847688A947DB2A54F5878");
        requestBuilder.setAuthInfo(authInfo);
        String response = api.post(requestBuilder);
        System.out.println(response);
    }


    @Test
    void getNumberList() throws OpenSdkException {
        RequestBuilder requestBuilder = new RequestBuilder();
        requestBuilder.setUrl("/open-api/goods/number-list");
        requestBuilder.setMethod(HttpMethodEnum.GET);

        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("per_page", "50");
        map.put("type", "1");
        requestBuilder.setQueryParams(map);
        // 🚨 WARNING: This is a TEST key, do NOT use it in production! 🚨
        AuthInfo authInfo = AuthInfo.buildAuthInfo(SignModeEnum.OPEN_KEY_ID).withAppid("11795D14130008FA5B4FF15DFCADB").withOpenKeyId("5C83782096BA46008D66C424CB39803F").withAppSecret("CD220D80B31E48A69FEC0FB6D7223421").withSecretKey("283598F3DEA847688A947DB2A54F5878");
        requestBuilder.setAuthInfo(authInfo);
        String response = api.get(requestBuilder);
        System.out.println(response);
    }


    @Test
    void decryptEventData() {
        // 🚨 WARNING: This is a TEST data, do NOT use it in production! 🚨
        String eventData = "rgDgjyfcT6pNEW+A6LB23hvZGZ5EpE8DdF2jXgQjxln1CSzU8916ftvBn4wF1bnurJFZA1Qt2oGHAIOMqSKAng==";
        // 🚨 WARNING: This is a TEST key, do NOT use it in production! 🚨
        String response = api.decryptEventData(eventData, "283598F3DEA847688A947DB2A54F5878");
        System.out.println(response);
    }


    @Test
    void decryptSecretKey() throws OpenSdkException {
        // 🚨 WARNING: This is a TEST secretKey, do NOT use it in production! 🚨
        String secretKey = "tRaAgVm76SGoW3Uv8+BQze8v5QT2DD37Qj4BxRG8TB13BeNOl1BYbaBBGkXROxoQ";
        // 🚨 WARNING: This is a TEST key, do NOT use it in production! 🚨
        String response = api.decryptSecretKey(secretKey, "CD220D80B31E48A69FEC0FB6D7223421");
        System.out.println(response);
    }
}
