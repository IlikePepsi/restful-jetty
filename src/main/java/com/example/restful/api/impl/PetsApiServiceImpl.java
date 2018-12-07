package com.example.restful.api.impl;

import com.example.restful.api.*;
import com.example.restful.model.*;

import com.example.restful.model.Error;
import com.example.restful.model.Pet;

import java.util.ArrayList;
import java.util.List;
import com.example.restful.api.NotFoundException;
import org.apache.commons.lang3.StringUtils;

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

    private Pet petId() {
        return new Pet().id(generateId());
    }

    @Override
    public Response createPet(PetPrototype petPrototype, SecurityContext securityContext) throws NotFoundException {

        if (petPrototype == null) {
            Error errorResponse = new Error().code(400).message("Request body must not be empty and represent the PetPrototype.");

            return Response.status(400).entity(errorResponse).build();
        }

        Pet p = new Pet().id(generateId());
        p.setName(petPrototype.getName());
        p.setTag(petPrototype.getTag());
        pets.add(p);

        // do some magic!
        return Response.ok().entity(null).build();
    }

    @Override
    public Response getPets( Integer limit, Integer index, SecurityContext securityContext) throws NotFoundException {

        List<Pet> responsePayload = null;
        String nextPageLink = StringUtils.EMPTY;

        // set default index
        if (index == null) {
            index = 0;
        }

        // set default limit
        if (limit == null) {
            limit = 100;
        }

        // handle empty list
        if (pets.isEmpty()) {
            return Response.ok().build();
        }

        // handle index too large
        if (index > pets.size()) {
            Error e = new Error().code(1).message("Index exceeds size of listed pets.");
            return Response.status(400).entity(e).build();
        }

        // handle any page in valid range
        if (pets.size() >= (index + limit)) {
            responsePayload = pets.subList(index, (index + limit));
        }

        // handle last page
        if (pets.size() < (index + limit)) {
            responsePayload = pets.subList(index, (index + (pets.size() % index)));
        }

        return Response.ok().entity(responsePayload).header(
                "X-next", "localhost:9090/api/pets" + "?limit=" + limit + "&index=" + (limit + index)).build();
    }

    @Override
    public Response showPetById(String petId, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
}
