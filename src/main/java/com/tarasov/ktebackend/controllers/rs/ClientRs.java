package com.tarasov.ktebackend.controllers.rs;

import com.tarasov.ktebackend.controllers.dto.GetClientsResponseDto;
import com.tarasov.ktebackend.controllers.dto.UpdateClientDiscountRequestDto;
import com.tarasov.ktebackend.controllers.dto.basic.BasicResponseDto;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("/clientRs")
public interface ClientRs {

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_XML)
    GetClientsResponseDto getClients();

    @POST
    @Path("/discount/{uuid}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    BasicResponseDto updateClientDiscount(
            @PathParam("uuid") UUID clientUuid,
            UpdateClientDiscountRequestDto request
    );

}
