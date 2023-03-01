package com.tarasov.ktebackend.service;

import com.tarasov.ktebackend.controllers.dto.GetProductInfoResponseDto;
import com.tarasov.ktebackend.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> getProducts();

    GetProductInfoResponseDto getProductInfoByIdWithInformation(UUID productUuid);

    void setProductRating(UUID clientUuid, UUID productUuid, Integer reiting);

    void removeDiscount();

    void setDiscountProductInRange(int minRange, int maxRange);


}
