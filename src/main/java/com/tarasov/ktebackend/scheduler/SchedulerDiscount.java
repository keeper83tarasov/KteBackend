package com.tarasov.ktebackend.scheduler;

import com.tarasov.ktebackend.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableScheduling
@EnableAsync
@RequiredArgsConstructor
public class SchedulerDiscount {
    private final ProductService productService;

    private static final int MIN_DISCOUNT = 5;
    private static final int MAX_DISCOUNT = 10;

    @Scheduled(fixedRateString = "PT01H")
    @Async
    void setRandomDiscountForRandomProduct() {
        productService.removeDiscount();
        productService.setDiscountProductInRange(MIN_DISCOUNT, MAX_DISCOUNT);
    }
}
