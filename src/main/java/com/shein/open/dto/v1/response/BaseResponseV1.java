package com.shein.open.dto.v1.response;

import lombok.Data;

/**
 * Author: Haolun Yu
 * Date: 24/02/2025
 * Time: 11:39
 */

@Data
public class BaseResponseV1 {
    // Response Code Successful: 0
    private String code;
    // Error description, success: OK
    private String msg;
    // The unique identifier of the request; used for exception tracking
    private String traceId;
}
