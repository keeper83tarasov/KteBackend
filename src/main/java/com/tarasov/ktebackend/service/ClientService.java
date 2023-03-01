package com.tarasov.ktebackend.service;

import com.tarasov.ktebackend.entity.Client;

import java.util.List;
import java.util.UUID;


public interface ClientService {
    List<Client> getClients();

    void changeDiscount(UUID clientUuid, Integer discountFirst, Integer discountSecond);
}
