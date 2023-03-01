package com.tarasov.ktebackend.controllers.ws;

import com.tarasov.ktebackend.controllers.dto.GetClientsResponseDto;
import com.tarasov.ktebackend.controllers.dto.UpdateClientDiscountRequestDto;
import com.tarasov.ktebackend.controllers.dto.basic.BasicResponseDto;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.UUID;

@WebService
public interface ClientWs {

    @WebMethod
    GetClientsResponseDto getClients();

    @WebMethod
    BasicResponseDto updateClientDiscount(
            @WebParam(name = "uuid") UUID clientUuid,
            UpdateClientDiscountRequestDto request
    );
}
