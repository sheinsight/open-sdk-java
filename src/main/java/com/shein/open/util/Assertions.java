package com.shein.open.util;


/**
 * Assertions
 */
public final class Assertions {
    private Assertions() {
    }

    /**
     * check null
     * @param obj obj
     * @param message message
     */
    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * check blank
     * @param obj obj
     * @param message message
     */
    public static void notBlank(Object obj, String message) {
        if (obj == null || "".equals(obj) || (obj instanceof String && ((String) obj).trim().isEmpty())) {
            throw new IllegalArgumentException(message);
        }
    }
}
