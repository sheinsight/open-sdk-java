package com.shein.open.dto.v1.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Author: Haolun Yu
 * Date: 24/02/2025
 * Time: 12:43
 */

//https://open.sheincorp.com/documents/apidoc/detail/3000704
@EqualsAndHashCode(callSuper = true)
@lombok.Data
public class ProductListResponseV1 extends BaseResponseV1{

    @JsonProperty("info")
    private Info info;

    @lombok.Data
    public static class Info{
        private List<Data> data;
    }

    @lombok.Data
    public static class Data{
        //platform skcName
        private String skcName;
        //SKU dimension data, up to 40 SKUs per SKC
        private List<String> skuCodeList;
        //platform spuName
        private String spuName;
    }
}
