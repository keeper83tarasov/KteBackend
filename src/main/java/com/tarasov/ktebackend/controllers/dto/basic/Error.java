package com.tarasov.ktebackend.controllers.dto.basic;

import lombok.Builder;
import lombok.Getter;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Builder
@XmlRootElement(name = "Error")
public class Error {
    private final int code;
    private final String message;
}
