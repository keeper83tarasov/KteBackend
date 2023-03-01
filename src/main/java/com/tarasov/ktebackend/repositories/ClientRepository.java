package com.tarasov.ktebackend.repositories;


import com.tarasov.ktebackend.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("select distinct c from Client c where c.uuid = :uuid")
    Optional<Client> findByUuid(@Param("uuid") UUID uuid);

}
