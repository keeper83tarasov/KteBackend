package com.tarasov.ktebackend.controllers.rs;

import com.tarasov.ktebackend.controllers.dto.GetProductInfoResponseDto;
import com.tarasov.ktebackend.controllers.dto.GetProductResponseDto;
import com.tarasov.ktebackend.controllers.dto.SetProductRatingRequestDto;
import com.tarasov.ktebackend.controllers.dto.basic.BasicResponseDto;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("/productRs")
public interface ProductRs {

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_XML)
    GetProductResponseDto getProducts();

    @GET
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_XML)
    GetProductInfoResponseDto getProductInfoById(
            @PathParam("uuid") UUID productUuid
    );

    @POST
    @Path("/rating")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    BasicResponseDto setProductRating(
            SetProductRatingRequestDto request
    );
}
