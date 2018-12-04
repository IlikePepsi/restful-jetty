package com.example.restful.api.impl;

import com.example.restful.api.*;
import com.example.restful.model.*;

import com.example.restful.model.Error;
import com.example.restful.model.Pet;

import java.util.ArrayList;
import java.util.List;
import com.example.restful.api.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2018-12-03T15:31:09.107+01:00[Europe/Berlin]")
public class PetsApiServiceImpl extends PetsApiService {

    private List<Pet> pets = new ArrayList<>();

    private long generateId() {
        long leftLimit = 1000000L;
        long rightLimit = 10000000L;

        return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }

    @Override
    public Response createPets(SecurityContext securityContext) throws NotFoundException {

        pets.add(new Pet().id(generateId()).name("Joey").tag("dog"));
        pets.add(new Pet().id(generateId()).name("MaryLou").tag("cat"));
        pets.add(new Pet().id(generateId()).name("Rico").tag("budgie"));

        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    @Override
    public Response listPets( Integer limit, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    @Override
    public Response showPetById(String petId, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
}
