package com.tarasov.ktebackend.controllers.rs;

import com.tarasov.ktebackend.controllers.dto.GetFinalPriceRequestDto;
import com.tarasov.ktebackend.controllers.dto.GetFinalPriceResponseDto;
import com.tarasov.ktebackend.controllers.dto.GetStatisticRequestDto;
import com.tarasov.ktebackend.controllers.dto.GetStatisticResponseDto;
import com.tarasov.ktebackend.controllers.dto.RegisterSaleRequestDto;
import com.tarasov.ktebackend.controllers.dto.basic.BasicResponseDto;
import com.tarasov.ktebackend.service.SaleService;
import com.tarasov.ktebackend.utils.Validations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.tarasov.ktebackend.utils.Validations.checkUuid;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaleRsImpl implements SaleRs {
    private final SaleService saleService;

    @Override
    public GetFinalPriceResponseDto getFinalPrice(GetFinalPriceRequestDto request) {
        Validations.checkNotNull(request.getClientUuid(), "clientUuid");
        Validations.checkUuid(request.getClientUuid(), "clientUuid");
        Validations.checkNotNullOrEmptyMap(request.getProductUuidsWithQuantities(), "productUuidsWithQuantities");

        Long finalPrice = saleService.getFinalPrice(request.getClientUuid(), request.getProductUuidsWithQuantities());
        return GetFinalPriceResponseDto.builder().finalPrice(finalPrice).build();
    }

    @Override
    public BasicResponseDto registerSale(RegisterSaleRequestDto request) {
        Validations.checkNotNull(request.getClientUuid(), "clientUuid");
        Validations.checkUuid(request.getClientUuid(), "clientUuid");
        Validations.checkNotNegativeLong(request.getPrice(), "price");
        Validations.checkNotNullOrEmptyMap(request.getProductUuidsWithQuantities(), "productUuidsWithQuantities");

        saleService.registerSale(request.getClientUuid(), request.getProductUuidsWithQuantities(), request.getPrice());

        return new BasicResponseDto();
    }

    @Override
    public GetStatisticResponseDto getStatistic(GetStatisticRequestDto request) {
        Validations.checkTwoParametrsNotNull(request.getClientUuid(), request.getProductUuid(), "clientUuid and productUuid");
        if (request.getClientUuid() != null) {
            Validations.checkUuid(request.getClientUuid(), "clientUuid");
        }
        if (request.getProductUuid() != null) {
            Validations.checkUuid(request.getProductUuid(), "productUuid");
        }

        return saleService.getStatistic(request.getClientUuid(), request.getProductUuid());
    }
}
