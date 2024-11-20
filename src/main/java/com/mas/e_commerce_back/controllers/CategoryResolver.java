package com.mas.e_commerce_back.controllers;

import com.mas.e_commerce_back.dtos.ApiResponse;
import com.mas.e_commerce_back.entities.Category;
import com.mas.e_commerce_back.inputs.CategoryDetailsInput;
import com.mas.e_commerce_back.inputs.CategoryPositionInput;
import com.mas.e_commerce_back.services.CategoryService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Component
@GraphQLApi
@Validated
public class CategoryResolver {

    private final CategoryService categoryService;


    @Autowired
    public CategoryResolver(CategoryService categoryService) {
        this.categoryService = categoryService;

    }

    // create category graphql mutation
    @GraphQLMutation(description = "create a new category")
    public ApiResponse<Category> createCategory(@Valid @GraphQLNonNull @GraphQLArgument CategoryDetailsInput detailsInput) {
        Category categoryEntity = categoryService.createCategory(detailsInput);
        return new ApiResponse<>(
                true,
                "category created successfully",
                categoryEntity
        );
    }




    @GraphQLQuery( description = "get all categories by section id")
    public ApiResponse<List<Category>> getCategoriesBySectionId(@GraphQLNonNull @GraphQLArgument Integer sectionId) {
        return new ApiResponse<>(
                true,
                "categories retrieved successfully",
                categoryService.getCategoriesBySectionId(sectionId)

        );

    }

    // get category by id
    @GraphQLQuery(description = "get category by id")
    public ApiResponse<Category> getCategoryById(@GraphQLNonNull @GraphQLArgument Integer id) {
        return new ApiResponse<>(
                true,
                "category retrieved successfully",
                categoryService.getCategoryById(id)
        );
    }

    @GraphQLQuery(description = "get category by name")
    public ApiResponse<Category> getCategoryByName(@GraphQLNonNull @GraphQLArgument String name) {
        return new ApiResponse<>(
                true,
                "category retrieved successfully",
                categoryService.getCategoryByName(name)
        );
    }

    // update category details
    @GraphQLMutation(description = "update category details")
    public ApiResponse<Category> updateCategoryDetails(
            @GraphQLNonNull @GraphQLArgument CategoryDetailsInput categoryDetailsInput){
        return new ApiResponse<>(
                true,
            "category updated successfully",
            categoryService.updateCategoryDetails(categoryDetailsInput)
        );

    }

    // delete category by id
    @GraphQLMutation(description = "delete category by id")
    public ApiResponse<Boolean> deleteCategoryById(@GraphQLNonNull @GraphQLArgument Integer id) {
        return new ApiResponse<>(
                true,
                "category deleted successfully",
                categoryService.deleteCategory(id)
        );
    }

    // sort categories by alphabetical order
    @GraphQLMutation(description = "sort categories by alphabetical order")
    public ApiResponse<List<Category>> sortCategoriesByAlphabeticalOrder(@GraphQLNonNull @GraphQLArgument Integer sectionId) {
        return new ApiResponse<>(
                true,
                "categories sorted by alphabetical order",
                categoryService.sortCategoriesByAlphabeticalOrder(sectionId)
        );
    }

    // swap categories positions
    @GraphQLMutation(description = "swap categories positions")
    public ApiResponse<List<Category>> swapCategoriesPositions(@Valid @GraphQLNonNull @GraphQLArgument List<CategoryPositionInput> categoryPositionInput) {
        return new ApiResponse<>(
                true,
                "categories positions updated successfully",
                categoryService.swapCategoriesPositions(categoryPositionInput)
        );
    }




}
