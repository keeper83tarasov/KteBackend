package com.tarasov.ktebackend.repositories;

import com.tarasov.ktebackend.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, BigDecimal> {
    @Query("select distinct r from Rating r where r.product.id = :productId")
    List<Rating> findAllByProductId(@Param("productId") Long productId);

    @Query("select distinct r from Rating r where r.clientId = :clientId and r.product.id = :productId")
    Optional<Rating> findByClientIdAndProductId(@Param("clientId") Long clientId, @Param("productId") Long productId);

}
