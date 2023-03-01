package com.tarasov.ktebackend.controllers.rest;

import com.tarasov.ktebackend.controllers.dto.GetFinalPriceRequestDto;
import com.tarasov.ktebackend.controllers.dto.GetFinalPriceResponseDto;
import com.tarasov.ktebackend.controllers.dto.GetStatisticRequestDto;
import com.tarasov.ktebackend.controllers.dto.GetStatisticResponseDto;
import com.tarasov.ktebackend.controllers.dto.RegisterSaleRequestDto;
import com.tarasov.ktebackend.controllers.dto.basic.BasicResponseDto;
import com.tarasov.ktebackend.service.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tarasov.ktebackend.utils.Validations.checkNotNegativeLong;
import static com.tarasov.ktebackend.utils.Validations.checkNotNull;
import static com.tarasov.ktebackend.utils.Validations.checkNotNullOrEmptyMap;
import static com.tarasov.ktebackend.utils.Validations.checkTwoParametrsNotNull;
import static com.tarasov.ktebackend.utils.Validations.checkUuid;

@RestController
@RequestMapping("/api2/sale")
@Slf4j
@RequiredArgsConstructor
public class SaleController {
    private final SaleService saleService;

    @PostMapping("/price")
    public GetFinalPriceResponseDto getFinalPrice(@RequestBody GetFinalPriceRequestDto request) {
        checkNotNull(request.getClientUuid(), "clientUuid");
        checkUuid(request.getClientUuid(), "clientUuid");
        checkNotNullOrEmptyMap(request.getProductUuidsWithQuantities(), "productUuidsWithQuantities");

        Long finalPrice = saleService.getFinalPrice(request.getClientUuid(), request.getProductUuidsWithQuantities());
        return GetFinalPriceResponseDto.builder().finalPrice(finalPrice).build();
    }

    @PostMapping("/register")
    public BasicResponseDto registerSale(@RequestBody RegisterSaleRequestDto request) {
        checkNotNull(request.getClientUuid(), "clientUuid");
        checkUuid(request.getClientUuid(), "clientUuid");
        checkNotNegativeLong(request.getPrice(), "price");
        checkNotNullOrEmptyMap(request.getProductUuidsWithQuantities(), "productUuidsWithQuantities");

        saleService.registerSale(request.getClientUuid(), request.getProductUuidsWithQuantities(), request.getPrice());

        return new BasicResponseDto();
    }

    @PostMapping("/statistic")
    public GetStatisticResponseDto getStatistic(@RequestBody GetStatisticRequestDto request) {
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