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
@XmlRootElement(name = "GetStatisticResponseDto")
public class GetStatisticResponseDto extends BasicResponseDto {
    private Integer checkCount;
    Long priceSum;
    Long discountSum;

}
