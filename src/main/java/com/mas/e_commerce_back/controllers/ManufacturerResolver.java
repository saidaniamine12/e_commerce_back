package com.mas.e_commerce_back.controllers;

import com.mas.e_commerce_back.config.UploadScalar;
import com.mas.e_commerce_back.dtos.ApiResponse;
import com.mas.e_commerce_back.entities.Manufacturer;
import com.mas.e_commerce_back.inputs.ManufacturerInput;
import com.mas.e_commerce_back.services.ManufacturerService;
import graphql.schema.GraphQLScalarType;
import io.leangen.graphql.annotations.*;
import io.leangen.graphql.annotations.types.GraphQLType;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import io.leangen.graphql.spqr.spring.autoconfigure.DefaultGlobalContext;
import jakarta.servlet.http.Part;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;


@Component
@GraphQLApi
@Validated
public class ManufacturerResolver {

    private final ManufacturerService manufacturerService;

    @Autowired
    public ManufacturerResolver(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    // use ArgumentInjector to get the request object
    @GraphQLMutation(description = "create a new manufacturer")
    public ApiResponse<String> createManufacturer(String file) {
        System.out.println("object" + file);
        System.out.println("imageFile" );

        System.out.println();
        System.out.println("createManufacturerMutationn" );
        return new ApiResponse<>(
                true,
                "Manufacturer created successfully",
                "createManufacturerMutationn"
        );

    }

    // get all
    @GraphQLQuery(description = "get all manufacturers")
    public ApiResponse<List<Manufacturer>> getManufacturers() {
        return new ApiResponse<>(
                true,
                "manufacturers retrieved successfully",
                manufacturerService.getAllManufacturers()
        );
    }

    // get by id
    @GraphQLQuery(description = "get manufacturer by id")
    public ApiResponse<Manufacturer> getManufacturerById(@GraphQLNonNull @GraphQLArgument Integer id) {
        return new ApiResponse<>(
                true,
                "manufacturer retrieved successfully",
                manufacturerService.getManufacturerById(id)
        );
    }

    // get by name
    @GraphQLQuery(description = "get manufacturer by name")
    public ApiResponse<Manufacturer> getManufacturerByName(@GraphQLNonNull @GraphQLArgument String name) {
        return new ApiResponse<>(
                true,
                "manufacturer retrieved successfully",
                manufacturerService.getManufacturerByName(name)
        );
    }


//    // update manufacturer details
//    @GraphQLMutation(description = "update manufacturer details by id")
//    public ApiResponse<Manufacturer> updateManufacturerDetails(@Valid @GraphQLArgument ManufacturerInput manufacturerInput) {
//        return new ApiResponse<>(
//                true,
//                "manufacturer updated successfully",
//                manufacturerService.updateManufacturerDetails(manufacturerInput)
//        );
//    }

    // delete manufacturer by id
    @GraphQLMutation(description = "delete manufacturer by id")
    public ApiResponse<Boolean> deleteManufacturerById(@GraphQLNonNull @GraphQLArgument Integer id) {
        return new ApiResponse<>(
                true,
                "manufacturer deleted successfully",
                manufacturerService.deleteManufacturerById(id)
        );
    }

}
