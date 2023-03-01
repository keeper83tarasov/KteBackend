package com.tarasov.ktebackend.controllers.ws;

import com.tarasov.ktebackend.controllers.dto.GetProductInfoResponseDto;
import com.tarasov.ktebackend.controllers.dto.GetProductResponseDto;
import com.tarasov.ktebackend.controllers.dto.SetProductRatingRequestDto;
import com.tarasov.ktebackend.controllers.dto.basic.BasicResponseDto;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.UUID;

@WebService
public interface ProductWs {
    @WebMethod
    GetProductResponseDto getProducts();

    @WebMethod
    GetProductInfoResponseDto getProductInfoById(@WebParam(name = "uuid") UUID productUuid);

    @WebMethod
    BasicResponseDto setProductRating(SetProductRatingRequestDto request);
}
