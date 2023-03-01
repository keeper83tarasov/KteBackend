package com.tarasov.ktebackend.utils.exception;

import java.util.Arrays;
import java.util.HashMap;

public enum BusinessExceptions implements IBusinessExceptionEnumMessage {
    ENTITY_NOT_FOUND("Entity [%s] with Uuid[%s] not found.", "ENTITY_NOT_FOUND"),
    BUSINESS_RULES_VALIDATION_FAIL("Business rules not being followed.", "BUSINESS_RULES_VALIDATION_FAIL");

    private final String message;
    private final String code;

    private static final HashMap<String, BusinessExceptions> cache;

    static {
        cache = new HashMap<>(values().length, 1);
        Arrays.stream(values()).forEach(e -> cache.put(e.getCode(), e));
    }

    private static BusinessExceptions getByCodeLocal(String code) {
        return cache.get(code);
    }

    BusinessExceptions(String message, String code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public IBusinessExceptionEnumMessage getByCode(String code) {
        return getByCodeLocal(code);
    }
}
