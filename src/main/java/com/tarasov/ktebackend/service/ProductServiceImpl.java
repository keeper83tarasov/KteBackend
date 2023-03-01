package com.tarasov.ktebackend.service;

import com.tarasov.ktebackend.controllers.dto.GetProductInfoResponseDto;
import com.tarasov.ktebackend.entity.Client;
import com.tarasov.ktebackend.entity.Product;
import com.tarasov.ktebackend.entity.Rating;
import com.tarasov.ktebackend.repositories.ClientRepository;
import com.tarasov.ktebackend.repositories.PositionRepository;
import com.tarasov.ktebackend.repositories.ProductRepository;
import com.tarasov.ktebackend.repositories.RatingRepository;
import com.tarasov.ktebackend.utils.exception.BusinessException;
import com.tarasov.ktebackend.utils.exception.BusinessExceptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final RatingRepository ratingRepository;
    private final PositionRepository positionRepository;
    private final ClientRepository clientRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public GetProductInfoResponseDto getProductInfoByIdWithInformation(UUID productUuid) {

        Product product = productRepository.findByUuid(productUuid)
                .orElseThrow(() -> new BusinessException(
                                String.format(BusinessExceptions.ENTITY_NOT_FOUND.getMessage(), "Product", productUuid)
                        )
                );
        List<Rating> ratings = ratingRepository.findAllByProductId(product.getId());

        BigDecimal averageRating;
        int currentRating;
        if (ratings.isEmpty()) {
            averageRating = BigDecimal.ZERO;
            currentRating = 0;
        } else {
            averageRating = (BigDecimal.valueOf(sumRating(ratings))
                    .divide(BigDecimal.valueOf(ratings.size()), 1, RoundingMode.DOWN));
            currentRating = ratings.stream().max(Comparator.comparingLong(Rating::getId)).get().getRating();
        }

        Map<Integer, Integer> ratingPare = getAverageRating(ratings);

        return GetProductInfoResponseDto.builder()
                .note(product.getNote())
                .averageRating(averageRating)
                .distributionOfRating(ratingPare)
                .currentRating(currentRating)
                .build();

    }

    @Override
    @Transactional
    public void setProductRating(UUID clientUuid, UUID productUuid, Integer rating) {
        if ((rating != null && rating < 1) || (rating != null && rating > 5)) {
            throw new BusinessException(
                    String.format(BusinessExceptions.BUSINESS_RULES_VALIDATION_FAIL.getMessage())
            );
        }
        Client client = clientRepository.findByUuid(clientUuid)
                .orElseThrow(() -> new BusinessException(
                                String.format(BusinessExceptions.ENTITY_NOT_FOUND.getMessage(), "Client", clientUuid)
                        )
                );
        Product product = productRepository.findByUuid(productUuid)
                .orElseThrow(() -> new BusinessException(
                                String.format(BusinessExceptions.ENTITY_NOT_FOUND.getMessage(), "Product", productUuid)
                        )
                );

        Long clientId = client.getId();
        Long productId = product.getId();

        if (isBuy(clientId, productId)) {
            Optional<Rating> ratingFromDb = ratingRepository.findByClientIdAndProductId(clientId, productId);
            if (ratingFromDb.isPresent()) {
                changeOrDeleteOldRating(rating, ratingFromDb.get());
            } else {
                saveNewRating(rating, clientId, product);
            }
        } else {
            log.debug("Клиент с id:[{}] не приобретал товар с id[{}] ", clientId, productId);
        }
    }

    @Override
    @Transactional
    public void removeDiscount() {
        List<Product> productsWithDiscount = productRepository.findAllWithDiscount();
        productsWithDiscount.forEach(p -> p.setDiscount(0));
        productRepository.saveAll(productsWithDiscount);
    }

    @Override
    @Transactional
    public void setDiscountProductInRange(int minRange, int maxRange) {
        List<Product> products = productRepository.findAll();
        Product product = products.get(new Random().nextInt(products.size()));
        product.setDiscount(new Random().nextInt((maxRange - minRange) + 1) + minRange);
        productRepository.save(product);
    }

    private void changeOrDeleteOldRating(Integer rating, Rating ratingFromDb) {
        if (rating == null) {
            ratingRepository.delete(ratingFromDb);
        } else {
            ratingFromDb.setRating(rating);
            ratingRepository.save(ratingFromDb);
        }
    }

    private boolean isBuy(Long clientId, Long productId) {
        return !positionRepository.findAllByClientIdAndProductId(clientId, productId).isEmpty();
    }

    private void saveNewRating(Integer rating, Long clientId, Product product) {
        Rating newRating = Rating.builder()
                .rating(rating)
                .clientId(clientId)
                .product(product)
                .build();
        ratingRepository.save(newRating);
    }

    private Map<Integer, Integer> getAverageRating(List<Rating> ratings) {
        return ratings.stream().mapToInt(Rating::getRating).collect(HashMap::new, (m, c) -> {
            m.put(c, m.containsKey(c) ? (m.get(c) + 1) : 1);
        }, HashMap::putAll);
    }

    private Integer sumRating(List<Rating> ratings) {
        return ratings.stream().map(Rating::getRating).
                mapToInt(i -> i).sum();
    }
}

