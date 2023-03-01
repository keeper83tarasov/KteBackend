package com.tarasov.ktebackend.controllers.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;
import java.util.UUID;

@Data
@XmlRootElement(name = "GetFinalPriceRequestDto")
public class GetFinalPriceRequestDto {
    private UUID clientUuid;
    private Map<UUID, Integer> productUuidsWithQuantities;
}
