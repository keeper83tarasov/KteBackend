package com.tarasov.ktebackend.controllers.ws;

import com.tarasov.ktebackend.controllers.dto.GetFinalPriceRequestDto;
import com.tarasov.ktebackend.controllers.dto.GetFinalPriceResponseDto;
import com.tarasov.ktebackend.controllers.dto.GetStatisticRequestDto;
import com.tarasov.ktebackend.controllers.dto.GetStatisticResponseDto;
import com.tarasov.ktebackend.controllers.dto.RegisterSaleRequestDto;
import com.tarasov.ktebackend.controllers.dto.basic.BasicResponseDto;
import com.tarasov.ktebackend.service.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

import static com.tarasov.ktebackend.utils.Validations.checkNotNegativeLong;
import static com.tarasov.ktebackend.utils.Validations.checkNotNull;
import static com.tarasov.ktebackend.utils.Validations.checkNotNullOrEmptyMap;
import static com.tarasov.ktebackend.utils.Validations.checkTwoParametrsNotNull;
import static com.tarasov.ktebackend.utils.Validations.checkUuid;

@Slf4j
@Service
@RequiredArgsConstructor
@WebService(endpointInterface = "com.tarasov.ktebackend.controllers.ws.SaleWs", serviceName = "SaleWs")
public class SaleWsImpl implements SaleWs {

    private final SaleService saleService;

    @Override
    public GetFinalPriceResponseDto getFinalPrice(GetFinalPriceRequestDto request) {
        checkNotNull(request.getClientUuid(), "clientUuid");
        checkUuid(request.getClientUuid(), "clientUuid");
        checkNotNullOrEmptyMap(request.getProductUuidsWithQuantities(), "productUuidsWithQuantities");

        Long finalPrice = saleService.getFinalPrice(request.getClientUuid(), request.getProductUuidsWithQuantities());
        return GetFinalPriceResponseDto.builder().finalPrice(finalPrice).build();
    }

    @Override
    public BasicResponseDto registerSale(RegisterSaleRequestDto request) {
        checkNotNull(request.getClientUuid(), "clientUuid");
        checkUuid(request.getClientUuid(), "clientUuid");
        checkNotNegativeLong(request.getPrice(), "price");
        checkNotNullOrEmptyMap(request.getProductUuidsWithQuantities(), "productUuidsWithQuantities");

        saleService.registerSale(request.getClientUuid(), request.getProductUuidsWithQuantities(), request.getPrice());

        return new BasicResponseDto();
    }

    @Override
    public GetStatisticResponseDto getStatistic(GetStatisticRequestDto request) {
        checkTwoParametrsNotNull(request.getClientUuid(), request.getProductUuid(), "clientUuid and productUuid");
        if (request.getClientUuid() != null) {
            checkUuid(request.getClientUuid(), "clientUuid");
        }
        if (request.getProductUuid() != null) {
            checkUuid(request.getProductUuid(), "productUuid");
        }

        return saleService.getStatistic(request.getClientUuid(), request.getProductUuid());
    }


}

