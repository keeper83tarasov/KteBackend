package com.tarasov.ktebackend.repositories;

import com.tarasov.ktebackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select distinct p from Product p LEFT JOIN FETCH p.ratings where p.uuid = :uuid")
    Optional<Product> findByUuid(@Param("uuid")UUID uuid);

    @Query("select distinct p from Product p where p.discount is not null")
    List<Product> findAllWithDiscount();

}
