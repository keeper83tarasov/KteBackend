package com.tarasov.ktebackend.controllers.rs;

import com.tarasov.ktebackend.controllers.dto.GetClientsResponseDto;
import com.tarasov.ktebackend.controllers.dto.UpdateClientDiscountRequestDto;
import com.tarasov.ktebackend.controllers.dto.basic.BasicResponseDto;
import com.tarasov.ktebackend.entity.Client;
import com.tarasov.ktebackend.service.ClientService;
import com.tarasov.ktebackend.utils.mapper.DtoMapper;
import com.tarasov.ktebackend.utils.Validations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.tarasov.ktebackend.utils.Validations.checkUuid;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientRsImpl implements ClientRs {
    private final ClientService clientService;
    private final DtoMapper dtoMapper;

    @Override
    public GetClientsResponseDto getClients() {
        List<Client> clients = clientService.getClients();
        return GetClientsResponseDto.builder()
                .clients(
                        clients.stream()
                                .map(dtoMapper::clientToDto)
                                .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public BasicResponseDto updateClientDiscount(UUID clientUuid, UpdateClientDiscountRequestDto request) {
        Validations.checkNotNull(clientUuid, "clientUuid");
        Validations.checkUuid(clientUuid, "clientUuid");
        Validations.checkNotNegativeInteger(request.getDiscountFirst(), "discountFirst");
        Validations.checkNotNegativeInteger(request.getDiscountSecond(), "discountSecond");

        clientService.changeDiscount(clientUuid, request.getDiscountFirst(), request.getDiscountSecond());

        return new BasicResponseDto();
    }


}
