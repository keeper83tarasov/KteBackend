package com.tarasov.ktebackend.utils.exception;

public interface IBusinessExceptionEnumMessage {
    String getMessage();

    default String getMessage(Object... args) {
        return String.format(getMessage(), args);
    }

    String getCode();

    IBusinessExceptionEnumMessage getByCode(String code);
}
