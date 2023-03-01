package com.tarasov.ktebackend.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "ClientDto")
public class ClientDto {
    private UUID uuid;
    private String name;
    private Integer discountFirst;
    private Integer discountSecond;
}