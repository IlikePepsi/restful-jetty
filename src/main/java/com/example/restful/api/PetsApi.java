package com.example.restful.api;

import com.example.restful.model.Error;
import com.example.restful.model.Pet;
import com.example.restful.model.PetPrototype;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/pets")
@Api(description = "the pets API")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2023-12-18T20:30:54.854128501+01:00[Europe/Berlin]")
public class PetsApi {

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Create a pet", notes = "", response = Integer.class, tags={ "pets" })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Expected response to a valid request", response = Integer.class),
        @ApiResponse(code = 200, message = "Unexpected error", response = Error.class)
    })
    public Response createPet(@Valid @NotNull PetPrototype petPrototype) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Produces({ "application/json" })
    @ApiOperation(value = "List all pets", notes = "", response = Pet.class, responseContainer = "List", tags={ "pets" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Expected response to a valid request", response = Pet.class, responseContainer = "List"),
        @ApiResponse(code = 200, message = "Unexpected error", response = Error.class)
    })
    public Response getPets(@QueryParam("limit") @Max(100)  @ApiParam("How many items to return at one time (max 100)")  Integer limit,@QueryParam("index")  @ApiParam("Start index in the Pets list")  Integer index) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/{petId}")
    @Produces({ "application/json" })
    @ApiOperation(value = "Info for a specific pet", notes = "", response = Pet.class, tags={ "pets" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Expected response to a valid request", response = Pet.class),
        @ApiResponse(code = 200, message = "Unexpected error", response = Error.class)
    })
    public Response showPetById(@PathParam("petId") @Size(min=1) @ApiParam("The id of the pet to retrieve") String petId) {
        return Response.ok().entity("magic!").build();
    }
}
