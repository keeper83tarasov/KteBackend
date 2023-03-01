package com.tarasov.ktebackend.utils;

import com.tarasov.ktebackend.utils.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class Validations {
    private static final ConcurrentHashMap<String, Pattern> PATTERN_CACHE = new ConcurrentHashMap<>();

    private static final String EMAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    public static Throwable errorInstance(String message) {
        return new ValidationException(message);
    }

    public static Throwable errorInstanceOrPass(String message, Throwable ex) {
        if (ex instanceof ValidationException) {
            return ex;
        }
        return new ValidationException(message);
    }

    public static void error(String message) {
        throw new ValidationException(message);
    }

    public static void error(String message, Throwable ex) {
        throw new ValidationException(message, ex);
    }

    public static void checkNotNull(Object value, String paramName) {
        if (value == null) {
            error(String.format("Parameter '%s' is null", paramName));
        }
    }

    public static void checkTwoParametrsNotNull(Object valueFirst, Object valueSecond, String paramName) {
        if (valueFirst == null && valueSecond == null) {
            error(String.format("Parameters '%s' is null", paramName));
        }
    }

    public static void checkNotEmpty(String value, String paramName) {
        if (StringUtils.isBlank(value)) {
            error(String.format("Parameter '%s' is empty", paramName));
        }
    }

    public static void checkUuid(UUID valueForCheck, String paramName) {
        checkUuid(valueForCheck.toString(), paramName);
    }

    public static void checkUuid(String valueForCheck, String paramName) {
        checkNotEmpty(valueForCheck, paramName);
        checkPattern("[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}", valueForCheck, paramName);
    }

    public static void checkNotNegativeDouble(Double valueForCheck, String paramName) {
        checkNotNull(valueForCheck, paramName);
        checkTrue(valueForCheck >= 0, paramName);
    }

    public static void checkNotNegativeInteger(Integer valueForCheck, String paramName) {
        checkNotNull(valueForCheck, paramName);
        checkTrue(valueForCheck >= 0, paramName);
    }

    public static void checkNotNegativeLong(Long valueForCheck, String paramName) {
        checkNotNull(valueForCheck, paramName);
        checkTrue(valueForCheck >= 0, paramName);
    }

    public static void checkNotNegativeBigDecimal(BigDecimal valueForCheck, String paramName) {
        checkNotNull(valueForCheck, paramName);
        checkTrue(valueForCheck.compareTo(BigDecimal.ZERO) >= 0, paramName);
    }

    public static void checkNotNullOrEmptyMap(Map<UUID, Integer> valueForCheck, String paramName) {
        checkNotNull(valueForCheck, paramName);
        checkTrue(!valueForCheck.isEmpty(), paramName);
        valueForCheck.entrySet().stream().forEach(
                e -> {
                    checkNotNull(e.getKey(), paramName);
                    checkNotEmpty(e.getKey().toString(), paramName);

                    checkNotNull(e.getValue(), paramName);
                    checkNotNegativeInteger(e.getValue(), paramName);
                }
        );
    }

    public static void checkPattern(String pattern, String valueForCheck, String paramName) {
        if (!PATTERN_CACHE.computeIfAbsent(pattern, Pattern::compile).matcher(valueForCheck).matches()) {
            error(String.format("Parameter '%s' has illegal value, expected '%s'", paramName, pattern));
        }
    }

    public static void checkEqual(String expected, String valueForCheck, String paramName) {
        if (!expected.equals(valueForCheck)) {
            error(String.format("Parameter '%s' has illegal value, expected '%s'", paramName, expected));
        }
    }

    public static void checkLength(long expectedLength, String valueForCheck, String paramName) {
        if (valueForCheck.length() > expectedLength) {
            error(String.format("Parameter '%s' has illegal length, expected less than '%s'", paramName, expectedLength));
        }
    }

    public static void checkTrue(boolean condition, String paramName) {
        if (!condition) {
            error(String.format("Parameter '%s' has illegal value'", paramName));
        }
    }
}
