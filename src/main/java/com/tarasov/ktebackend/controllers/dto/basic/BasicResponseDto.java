package com.tarasov.ktebackend.controllers.dto.basic;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@Getter
@XmlRootElement(name = "BasicResponseDto")
public class BasicResponseDto {
    private final ResponseStatus status;
    private final String requestId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Error error;

    public BasicResponseDto() {
        this(null);
    }

    public BasicResponseDto(Error error) {
        this.status = error != null ? ResponseStatus.FAIL : ResponseStatus.SUCCESS;
        this.requestId = UUID.randomUUID().toString();
        this.error = error;
    }

}
