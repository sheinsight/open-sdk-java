package com.shein.open.exception;

/**
 * OpenSdkException
 */
public class OpenSdkException extends Exception {
    public OpenSdkException(String message) {
        super(message);
    }

    public OpenSdkException(String message, Throwable cause) {
        super(message, cause);
    }
}
