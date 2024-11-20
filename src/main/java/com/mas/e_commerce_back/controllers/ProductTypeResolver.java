package com.mas.e_commerce_back.controllers;


import com.mas.e_commerce_back.dtos.ApiResponse;
import com.mas.e_commerce_back.entities.ProductType;
import com.mas.e_commerce_back.inputs.ProductTypeInput;
import com.mas.e_commerce_back.inputs.ProductTypePositionInput;
import com.mas.e_commerce_back.services.ProductTypeService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@GraphQLApi
@Validated
public class ProductTypeResolver {

    private final ProductTypeService productTypeService;

    @Autowired
    public ProductTypeResolver(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }


    @GraphQLMutation(description = "create a new product type")
    public ApiResponse<ProductType> createProductType(@Valid @RequestBody ProductTypeInput productTypeInput) {
        return new ApiResponse<>(
                true,
                "Product type created successfully",
                productTypeService.createProductType(productTypeInput)
        );
    }

    @GraphQLMutation(description = "update a product type by id")
    public ApiResponse<ProductType> updateProductTypeDetails(@Valid @GraphQLArgument ProductTypeInput productTypeInput) {
        return new ApiResponse<>(
                true,
                "Product type updated successfully",
                productTypeService.updateProductTypeDetails(productTypeInput)
        );
    }

    @GraphQLMutation(description = "delete a product type by id")
    public ApiResponse<Boolean> deleteProductTypeById(@GraphQLNonNull @GraphQLArgument Integer id) {
        return new ApiResponse<>(
                true,
                "Product type deleted successfully",
                productTypeService.deleteProductType(id)
        );
    }

    @GraphQLQuery(description = "get product type by id")
    public ApiResponse<ProductType> getProductTypeById(@GraphQLNonNull @GraphQLArgument Integer id) {
        return new ApiResponse<>(
                true,
                "Product type fetched successfully",
                productTypeService.getProductTypeById(id)
        );
    }

    @GraphQLQuery(description = "get product type by name")
    public ApiResponse<ProductType> getProductTypeByName(@GraphQLNonNull @GraphQLArgument String name) {
        return new ApiResponse<>(
                true,
                "Product type fetched successfully",
                productTypeService.getProductTypeByName(name)
        );
    }

    @GraphQLQuery(description = "get all product types")
    public ApiResponse<List<ProductType>> getAllProductTypes(@GraphQLNonNull @GraphQLArgument Integer categoryId) {
        return new ApiResponse<>(
                true,
                "Product types fetched successfully",
                productTypeService.getAllProductTypesByCategoryId(categoryId)
        );
    }

    // swap
    @GraphQLMutation(description = "swap product types positions")
    public ApiResponse<List<ProductType>> swapProductTypesPositions(@Valid @GraphQLNonNull @GraphQLArgument List<ProductTypePositionInput> productTypePositionInputs) {
        return new ApiResponse<>(
                true,
                "Product types positions updated successfully",
                productTypeService.swapProductTypesPositions(productTypePositionInputs)
        );
    }

    // sort
    @GraphQLMutation(description = "sort product types by alphabetical order")
    public ApiResponse<List<ProductType>> sortProductTypesByAlphabeticalOrder(@GraphQLNonNull @GraphQLArgument Integer categoryId) {
        return new ApiResponse<>(
                true,
                "Product types sorted by alphabetical order",
                productTypeService.sortProductTypesByAlphabeticalOrder(categoryId)
        );
    }

}
