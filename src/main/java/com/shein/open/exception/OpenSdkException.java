package com.shein.open.exception;

/**
 * OpenSdkException
 */
public class OpenSdkException extends Exception {
    /**
     * OpenSdkException
     * @param message message
     * */
    public OpenSdkException(String message) {
        super(message);
    }

    /**
     * OpenSdkException
     * @param message message
     * @param cause cause
     * */
    public OpenSdkException(String message, Throwable cause) {
        super(message, cause);
    }
}
