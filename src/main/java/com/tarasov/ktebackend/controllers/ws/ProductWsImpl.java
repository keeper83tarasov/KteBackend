package com.tarasov.ktebackend.controllers.ws;

import com.tarasov.ktebackend.controllers.dto.GetProductInfoResponseDto;
import com.tarasov.ktebackend.controllers.dto.GetProductResponseDto;
import com.tarasov.ktebackend.controllers.dto.SetProductRatingRequestDto;
import com.tarasov.ktebackend.controllers.dto.basic.BasicResponseDto;
import com.tarasov.ktebackend.entity.Product;
import com.tarasov.ktebackend.service.ProductService;
import com.tarasov.ktebackend.utils.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.tarasov.ktebackend.utils.Validations.checkNotNull;
import static com.tarasov.ktebackend.utils.Validations.checkUuid;

@Slf4j
@Service
@RequiredArgsConstructor
@WebService(endpointInterface = "com.tarasov.ktebackend.controllers.ws.ProductWs", serviceName = "ProductWs")
public class ProductWsImpl implements ProductWs {
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
        checkNotNull(productUuid, "productUuid");
        checkUuid(productUuid, "productUuid");

        return productService.getProductInfoByIdWithInformation(productUuid);
    }

    @Override
    public BasicResponseDto setProductRating(SetProductRatingRequestDto request) {
        checkNotNull(request.getClientUuid(), "clientUuid");
        checkUuid(request.getClientUuid(), "clientUuid");
        checkNotNull(request.getProductUuid(), "productUuid");
        checkUuid(request.getProductUuid(), "productUuid");

        productService.setProductRating(request.getClientUuid(), request.getProductUuid(), request.getRating());

        return new BasicResponseDto();
    }

}
