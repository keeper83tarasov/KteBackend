package com.tarasov.ktebackend.repositories;

import com.tarasov.ktebackend.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM sale s WHERE s.sale_date = :date ORDER BY s.check_number DESC LIMIT 1")
    Optional<Sale> findLastSale(@Param("date") LocalDate date);

    @Query("select distinct s from Sale s LEFT JOIN FETCH s.positions where s.clientId = :clientId ")
    List<Sale> findSalesByClientId(@Param("clientId") Long clientId);

    @Query("select distinct s from Sale s LEFT JOIN FETCH s.positions p where p.productId = :productId ")
    List<Sale> findSalesByProductId(@Param("productId") Long productId);
}
