package com.tarasov.ktebackend.utils.mapper;

import com.tarasov.ktebackend.controllers.dto.ClientDto;
import com.tarasov.ktebackend.controllers.dto.ProductDto;
import com.tarasov.ktebackend.entity.Client;
import com.tarasov.ktebackend.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {
    public ClientDto clientToDto(Client client) {
        return ClientDto.builder()
                .uuid(client.getUuid())
                .name(client.getName())
                .discountFirst(client.getDiscountFirst())
                .discountSecond(client.getDiscountSecond())
                .build();
    }

    public ProductDto productToDto(Product product) {
        return ProductDto.builder()
                .uuid(product.getUuid())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
