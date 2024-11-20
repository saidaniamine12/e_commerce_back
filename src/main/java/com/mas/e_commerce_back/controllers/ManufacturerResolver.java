package com.mas.e_commerce_back.controllers;

import com.mas.e_commerce_back.dtos.ApiResponse;
import com.mas.e_commerce_back.entities.Manufacturer;
import com.mas.e_commerce_back.inputs.ManufacturerInput;
import com.mas.e_commerce_back.services.ManufacturerService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Component
@GraphQLApi
@Validated
public class ManufacturerResolver {

    private final ManufacturerService manufacturerService;

    @Autowired
    public ManufacturerResolver(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GraphQLMutation(description = "create a new manufacturer")
    public ApiResponse<Manufacturer> createManufacturer(@Valid @RequestBody ManufacturerInput manufacturerInput) {
        return new ApiResponse<>(
                true,
                "Manufacturer created successfully",
                manufacturerService
                        .createManufacturer(manufacturerInput)
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


    // update manufacturer details
    @GraphQLMutation(description = "update manufacturer details by id")
    public ApiResponse<Manufacturer> updateManufacturerDetails(@Valid @GraphQLArgument ManufacturerInput manufacturerInput) {
        return new ApiResponse<>(
                true,
                "manufacturer updated successfully",
                manufacturerService.updateManufacturerDetails(manufacturerInput)
        );
    }

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
