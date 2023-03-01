package com.tarasov.ktebackend.service;

import com.tarasov.ktebackend.entity.Client;
import com.tarasov.ktebackend.repositories.ClientRepository;
import com.tarasov.ktebackend.utils.exception.BusinessException;
import com.tarasov.ktebackend.utils.exception.BusinessExceptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    @Override
    @Transactional
    public void changeDiscount(UUID clientUuid, Integer discountFirst, Integer discountSecond) {
        Client client = clientRepository.findByUuid(clientUuid)
                .orElseThrow(() -> new BusinessException(
                                String.format(BusinessExceptions.ENTITY_NOT_FOUND.getMessage(), "Client", clientUuid)
                        )
                );
        client.setDiscountFirst(discountFirst);
        client.setDiscountSecond(discountSecond);
        clientRepository.save(client);
    }
}
