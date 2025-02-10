package com.shein.open.util;


/**
 * Assertions
 */
public final class Assertions {
    private Assertions() {
    }

    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notBlank(Object obj, String message) {
        if (obj == null || "".equals(obj)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isTrue(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }
}
