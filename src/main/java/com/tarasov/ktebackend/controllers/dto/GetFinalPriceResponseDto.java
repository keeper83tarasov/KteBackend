package com.tarasov.ktebackend.controllers.dto;

import com.tarasov.ktebackend.controllers.dto.basic.BasicResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "GetFinalPriceResponseDto")
public class GetFinalPriceResponseDto extends BasicResponseDto {
    Long finalPrice;
}
