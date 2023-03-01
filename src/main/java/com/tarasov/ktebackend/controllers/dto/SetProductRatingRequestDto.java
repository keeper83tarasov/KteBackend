package com.tarasov.ktebackend.controllers.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@Data
@XmlRootElement(name = "SetProductRatingRequestDto")
public class SetProductRatingRequestDto {
    private UUID clientUuid;
    private UUID productUuid;
    private Integer rating;
}
