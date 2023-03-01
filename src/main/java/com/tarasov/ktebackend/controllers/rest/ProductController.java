package com.tarasov.ktebackend.controllers.rest;

import com.tarasov.ktebackend.controllers.dto.GetProductInfoResponseDto;
import com.tarasov.ktebackend.controllers.dto.GetProductResponseDto;
import com.tarasov.ktebackend.controllers.dto.SetProductRatingRequestDto;
import com.tarasov.ktebackend.controllers.dto.basic.BasicResponseDto;
import com.tarasov.ktebackend.entity.Product;
import com.tarasov.ktebackend.service.ProductService;
import com.tarasov.ktebackend.utils.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.tarasov.ktebackend.utils.Validations.checkNotNull;
import static com.tarasov.ktebackend.utils.Validations.checkUuid;

@RestController
@RequestMapping("/api2/product")
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final DtoMapper dtoMapper;

    @GetMapping("/list")
    public GetProductResponseDto getProducts() {
        List<Product> products = productService.getProducts();
        return GetProductResponseDto.builder()
                .products(products.stream()
                        .map(dtoMapper::productToDto)
                        .collect(Collectors.toList())
                ).build();
    }

    @GetMapping("/{uuid}")
    public GetProductInfoResponseDto getProductInfoById(@PathVariable("uuid") UUID productUuid) {
        checkNotNull(productUuid, "productUuid");
        checkUuid(productUuid, "productUuid");

        return productService.getProductInfoByIdWithInformation(productUuid);
    }

    @PostMapping("/rating")
    public BasicResponseDto setProductRating(@RequestBody SetProductRatingRequestDto request) {
        checkNotNull(request.getClientUuid(), "clientUuid");
        checkUuid(request.getClientUuid(), "clientUuid");
        checkNotNull(request.getProductUuid(), "productUuid");
        checkUuid(request.getProductUuid(), "productUuid");

        productService.setProductRating(request.getClientUuid(), request.getProductUuid(), request.getRating());

        return new BasicResponseDto();
    }
}
