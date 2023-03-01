package com.tarasov.ktebackend.controllers.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "UpdateClientDiscountRequestDto")
public class UpdateClientDiscountRequestDto {
    private Integer discountFirst;
    private Integer discountSecond;
}
