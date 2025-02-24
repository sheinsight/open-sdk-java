package com.shein.open.dto.v1.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductListResponseV1Test {

    private ObjectMapper objectMapper;
    private final String serialiableString = "{\"code\":\"0\",\"msg\":\"OK\",\"traceId\":\"213r342dsadf\",\"info\":{\"data\":[{\"skcName\":\"skcTest\",\"skuCodeList\":[\"skuCode1\",\"skuCode2\"],\"spuName\":\"spuName\"}]}}";


    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void deserializationTest() throws JsonProcessingException {
        ProductListResponseV1 productListResponseV1 = objectMapper.readValue(serialiableString, ProductListResponseV1.class);
        assertEquals(serialiableString, objectMapper.writeValueAsString(productListResponseV1));
    }
}