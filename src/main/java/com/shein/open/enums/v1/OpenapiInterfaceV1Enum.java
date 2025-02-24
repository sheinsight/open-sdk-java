package com.shein.open.enums.v1;

import com.shein.open.dto.v1.response.BaseResponseV1;
import com.shein.open.dto.v1.response.ProductListResponseV1;
import lombok.Getter;

@Getter
public enum OpenapiInterfaceV1Enum {
    //https://open.sheincorp.com/documents/apidoc/detail/3000919
    //PRODUCT("/open-api/goods/product/publishOrEdit",Objects.class),
    //https://open.sheincorp.com/documents/apidoc/detail/3000769
    //PRODUCT_AUDIT_STATUS("/open-api/goods/query-document-state",Objects.class),
    //https://open.sheincorp.com/documents/apidoc/detail/3000704
    PRODUCT_LIST("/open-api/openapi-business-backend/product/query", ProductListResponseV1.class);

    private final String url;
    private final Class<? extends BaseResponseV1> responseClass;

    OpenapiInterfaceV1Enum(String url, Class<? extends BaseResponseV1> responseClass) {
        this.url = url;
        this.responseClass = responseClass;
    }
}
