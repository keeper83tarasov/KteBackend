package com.tarasov.ktebackend.repositories;

import com.tarasov.ktebackend.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    @Query("select distinct p from Position p where p.sale.clientId = :clientId and p.productId = :productId")
    List<Position> findAllByClientIdAndProductId(@Param("clientId") Long clientId, @Param("productId") Long productId);

}
