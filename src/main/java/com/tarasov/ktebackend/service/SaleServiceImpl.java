package com.tarasov.ktebackend.service;

import com.tarasov.ktebackend.controllers.dto.GetStatisticResponseDto;
import com.tarasov.ktebackend.entity.Client;
import com.tarasov.ktebackend.entity.Position;
import com.tarasov.ktebackend.entity.Product;
import com.tarasov.ktebackend.entity.Sale;
import com.tarasov.ktebackend.repositories.ClientRepository;
import com.tarasov.ktebackend.repositories.PositionRepository;
import com.tarasov.ktebackend.repositories.ProductRepository;
import com.tarasov.ktebackend.repositories.SaleRepository;
import com.tarasov.ktebackend.utils.exception.BusinessException;
import com.tarasov.ktebackend.utils.exception.BusinessExceptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final PositionRepository positionRepository;
    private final ClientRepository clientRepository;

    private static final int MAX_DISCONT = 18;

    @Override
    @Transactional(readOnly = true)
    public Long getFinalPrice(UUID clientUuid, Map<UUID, Integer> productIdsWithQuantities) {
        Client client = clientRepository.findByUuid(clientUuid)
                .orElseThrow(() -> new BusinessException(
                                String.format(BusinessExceptions.ENTITY_NOT_FOUND.getMessage(), "Client", clientUuid)
                        )
                );
        return calculateFinalPrice(productIdsWithQuantities, client);
    }

    @Override
    @Transactional
    public String registerSale(UUID clientUuid, Map<UUID, Integer> productIdsWithQuantities, Long price) {
        Client client = clientRepository.findByUuid(clientUuid)
                .orElseThrow(() -> new BusinessException(
                                String.format(BusinessExceptions.ENTITY_NOT_FOUND.getMessage(), "Client", clientUuid)
                        )
                );
        Long finalPriceSale = calculateFinalPrice(productIdsWithQuantities, client);
        if (finalPriceSale.compareTo(price) != 0) {
            throw new BusinessException(BusinessExceptions.BUSINESS_RULES_VALIDATION_FAIL.getMessage());
        }

        String checkNumber = getCheckNumber();
        List<Position> positions = getPositions(productIdsWithQuantities, client);
        Sale sale = formAndSaveSale(client.getId(), checkNumber, positions);
        positions.forEach(position -> position.setSale(sale));
        positionRepository.saveAll(positions);
        return checkNumber;
    }

    @Override
    @Transactional(readOnly = true)
    public GetStatisticResponseDto getStatistic(UUID clientUuid, UUID productUuid) {
        if (clientUuid != null && productUuid != null) {
            throw new BusinessException(BusinessExceptions.BUSINESS_RULES_VALIDATION_FAIL.getMessage());
        }

        if (clientUuid != null) {
            return formatStatisticForClient(clientUuid);
        } else {
            return formatStatisticForProduct(productUuid);
        }

    }

    private Long calculateFinalPrice(Map<UUID, Integer> productIdsWithQuantities, Client client) {
        return productIdsWithQuantities.entrySet().stream()
                .map(pair -> {
                    Product product = productRepository.findByUuid(pair.getKey())
                            .orElseThrow(() -> new BusinessException(
                                            String.format(BusinessExceptions.ENTITY_NOT_FOUND.getMessage(), "Product", pair.getKey())
                                    )
                            );
                    int quantity = pair.getValue();
                    int discountFinal = getFinalDiscount(quantity, client, product);

                    return getTotalPrice(quantity, discountFinal, product.getPrice());
                })
                .reduce(0L, Long::sum);
    }

    private String getCheckNumber() {
        String checkNumber;
        Sale lastSale = saleRepository.findLastSale(LocalDate.now()).orElse(null);
        if (lastSale != null) {
            int number = Integer.parseInt(lastSale.getCheckNumber()) + 1;
            checkNumber = StringUtils.right("00" + number, 5);
        } else {
            checkNumber = "00100";
        }
        return checkNumber;
    }

    private List<Position> getPositions(Map<UUID, Integer> productIdsWithQuantities, Client client) {
        return productIdsWithQuantities.entrySet().stream()
                .map(pair -> {
                    Product product = productRepository.findByUuid(pair.getKey())
                            .orElseThrow(() -> new BusinessException(
                                            String.format(BusinessExceptions.ENTITY_NOT_FOUND.getMessage(), "Product", pair.getKey())
                                    )
                            );
                    int quantity = pair.getValue();
                    Long defaultPrice = product.getPrice() * quantity;
                    int discountFinal = getFinalDiscount(quantity, client, product);
                    Long finalPrice = getTotalPrice(quantity, discountFinal, product.getPrice());

                    return Position.builder()

                            .productId(product.getId())
                            .quantity(quantity)
                            .defaultPrice(defaultPrice)
                            .finalPrice(finalPrice)
                            .finalDiscount(discountFinal)
                            .build();
                })
                .collect(Collectors.toList());
    }

    private Sale formAndSaveSale(Long clientId, String checkNumber, List<Position> positions) {
        Sale sale = Sale.builder()
                .clientId(clientId)
                .saleDate(LocalDate.now())
                .checkNumber(checkNumber)
                .positions(positions)
                .build();

        return saleRepository.save(sale);
    }

    private int getFinalDiscount(int quantity, Client client, Product product) {
        int discountClient = quantity >= 5 && client.getDiscountSecond() != 0 ? client.getDiscountSecond() : client.getDiscountFirst();
        int discountClientWithProduct = discountClient + product.getDiscount();
        return discountClientWithProduct < MAX_DISCONT ? discountClientWithProduct : MAX_DISCONT;
    }

    private Long getTotalPrice(int quantity, int discountFinal, Long price) {
        return (((price * quantity) * 100) - ((price * quantity) * discountFinal)) / 100;
    }

    private GetStatisticResponseDto formatStatisticForClient(UUID clientUuid) {
        Client client = clientRepository.findByUuid(clientUuid)
                .orElseThrow(() -> new BusinessException(
                                String.format(BusinessExceptions.ENTITY_NOT_FOUND.getMessage(), "Client", clientUuid)
                        )
                );

        List<Sale> sales = saleRepository.findSalesByClientId(client.getId());
        Long defaultPriceSum = sales.stream()
                .map(Sale::getPositions)
                .flatMap(positions -> positions.stream().map(Position::getDefaultPrice))
                .reduce(0L, Long::sum);

        Long finalPriceSum = sales.stream()
                .map(Sale::getPositions)
                .flatMap(positions -> positions.stream().map(Position::getFinalPrice))
                .reduce(0L, Long::sum);

        return GetStatisticResponseDto.builder()
                .checkCount(sales.size())
                .priceSum(defaultPriceSum)
                .discountSum(defaultPriceSum - finalPriceSum)
                .build();
    }

    private GetStatisticResponseDto formatStatisticForProduct(UUID productUuid) {
        Product product = productRepository.findByUuid(productUuid)
                .orElseThrow(() -> new BusinessException(
                                String.format(BusinessExceptions.ENTITY_NOT_FOUND.getMessage(), "Product", productUuid)
                        )
                );
        List<Sale> sales = saleRepository.findSalesByProductId(product.getId());
        Long defaultPriceSum = sales.stream()
                .map(Sale::getPositions)
                .flatMap(positions -> positions.stream().map(Position::getDefaultPrice))
                .reduce(0L, Long::sum);

        Long finalPriceSum = sales.stream()
                .map(Sale::getPositions)
                .flatMap(positions -> positions.stream().map(Position::getFinalPrice))
                .reduce(0L, Long::sum);

        return GetStatisticResponseDto.builder()
                .checkCount(sales.size())
                .priceSum(defaultPriceSum)
                .discountSum(defaultPriceSum - finalPriceSum)
                .build();
    }


}
