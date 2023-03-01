package com.tarasov.ktebackend.service;

import com.tarasov.ktebackend.controllers.dto.GetStatisticResponseDto;

import java.util.Map;
import java.util.UUID;

public interface SaleService {
    Long getFinalPrice(UUID clientUuid, Map<UUID, Integer> productIdsWithQuantities);

    String registerSale(UUID clientUuid, Map<UUID, Integer> productUuidsWithQuantities, Long price);

    GetStatisticResponseDto getStatistic(UUID clientUuid, UUID productUuid);
}
