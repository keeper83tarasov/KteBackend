package com.tarasov.ktebackend.controllers.dto;

import com.tarasov.ktebackend.controllers.dto.basic.BasicResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.Map;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "GetProductInfoResponseDto")
public class GetProductInfoResponseDto extends BasicResponseDto {
    private String note;
    private BigDecimal averageRating;
    private Map<Integer, Integer> distributionOfRating;
    private Integer currentRating;

}
