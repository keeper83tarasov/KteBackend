package com.tarasov.ktebackend.utils;

import com.tarasov.ktebackend.controllers.dto.basic.BasicResponseDto;
import com.tarasov.ktebackend.controllers.dto.basic.Error;
import com.tarasov.ktebackend.utils.exception.BusinessException;
import com.tarasov.ktebackend.utils.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
@Slf4j
@ResponseBody
public class ExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    public BasicResponseDto handleBusinessException(BusinessException ex) {
        log.error("Business error", ex);
        return new BasicResponseDto(Error.builder()
                .code(3)
                .message(ex.getMessage())
                .build());
    }

    @ExceptionHandler(ValidationException.class)
    public BasicResponseDto handleValidationException(ValidationException ex) {
        log.error("Validation error", ex);
        return new BasicResponseDto(Error.builder()
                .code(2)
                .message(ex.getMessage())
                .build());
    }

    @ExceptionHandler(Exception.class)
    public BasicResponseDto handleException(Exception ex) {
        log.error("Internal error", ex);
        return new BasicResponseDto(Error.builder()
                .code(1)
                .message("System error")
                .build());
    }
}
