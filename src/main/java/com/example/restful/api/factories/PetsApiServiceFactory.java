package com.example.restful.api.factories;

import com.example.restful.api.impl.PetsApiServiceImpl;
import com.example.restful.api.PetsApiService;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2018-12-03T15:31:09.107+01:00[Europe/Berlin]")
public class PetsApiServiceFactory {
    private final static PetsApiService service = new PetsApiServiceImpl();

    public static PetsApiService getPetsApi() {
        return service;
    }
}
