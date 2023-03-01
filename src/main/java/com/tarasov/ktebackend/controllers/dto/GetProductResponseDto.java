package com.tarasov.ktebackend.controllers.dto;

import com.tarasov.ktebackend.controllers.dto.basic.BasicResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "GetProductResponseDto")
public class GetProductResponseDto extends BasicResponseDto {
    private List<ProductDto> products;
}
