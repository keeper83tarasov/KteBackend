package com.tarasov.ktebackend.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@XmlRootElement(name = "GetStatisticRequestDto")
public class GetStatisticRequestDto {
    private UUID clientUuid;
    private UUID productUuid;

}
