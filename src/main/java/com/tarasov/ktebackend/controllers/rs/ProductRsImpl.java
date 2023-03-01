package com.tarasov.ktebackend.controllers.rs;

import com.tarasov.ktebackend.controllers.dto.GetProductInfoResponseDto;
import com.tarasov.ktebackend.controllers.dto.GetProductResponseDto;
import com.tarasov.ktebackend.controllers.dto.SetProductRatingRequestDto;
import com.tarasov.ktebackend.controllers.dto.basic.BasicResponseDto;
import com.tarasov.ktebackend.entity.Product;
import com.tarasov.ktebackend.service.ProductService;
import com.tarasov.ktebackend.utils.mapper.DtoMapper;
import com.tarasov.ktebackend.utils.Validations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.tarasov.ktebackend.utils.Validations.checkUuid;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductRsImpl implements ProductRs {
    private final ProductService productService;
    private final DtoMapper dtoMapper;

    @Override
    public GetProductResponseDto getProducts() {
        List<Product> products = productService.getProducts();
        return GetProductResponseDto.builder()
                .products(products.stream()
                        .map(dtoMapper::productToDto)
                        .collect(Collectors.toList())
                ).build();
    }

    @Override
    public GetProductInfoResponseDto getProductInfoById(UUID productUuid) {
        Validations.checkNotNull(productUuid, "productUuid");
        Validations.checkUuid(productUuid, "productUuid");

        return productService.getProductInfoByIdWithInformation(productUuid);
    }

    @Override
    public BasicResponseDto setProductRating(SetProductRatingRequestDto request) {
        Validations.checkNotNull(request.getClientUuid(), "clientUuid");
        Validations.checkUuid(request.getClientUuid(), "clientUuid");
        Validations.checkNotNull(request.getProductUuid(), "productUuid");
        Validations.checkUuid(request.getProductUuid(), "productUuid");

        productService.setProductRating(request.getClientUuid(), request.getProductUuid(), request.getRating());

        return new BasicResponseDto();
    }
}
