package com.tarasov.ktebackend.controllers.ws;

import com.tarasov.ktebackend.controllers.dto.GetFinalPriceRequestDto;
import com.tarasov.ktebackend.controllers.dto.GetFinalPriceResponseDto;
import com.tarasov.ktebackend.controllers.dto.GetStatisticRequestDto;
import com.tarasov.ktebackend.controllers.dto.GetStatisticResponseDto;
import com.tarasov.ktebackend.controllers.dto.RegisterSaleRequestDto;
import com.tarasov.ktebackend.controllers.dto.basic.BasicResponseDto;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface SaleWs {

    @WebMethod
    GetFinalPriceResponseDto getFinalPrice(GetFinalPriceRequestDto request);

    @WebMethod
    BasicResponseDto registerSale(RegisterSaleRequestDto request);

    @WebMethod
    GetStatisticResponseDto getStatistic(GetStatisticRequestDto request);
}
