package com.mas.e_commerce_back.controllers;

import com.mas.e_commerce_back.dtos.ApiResponse;
import com.mas.e_commerce_back.entities.Section;
import com.mas.e_commerce_back.inputs.SectionDetailsInput;
import com.mas.e_commerce_back.inputs.SectionPositionInput;
import com.mas.e_commerce_back.services.SectionService;
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
public class SectionResolver {


    private final SectionService sectionService;

    /* constructor injection of the service is preferred
    because it makes the class easier to test,
    it makes the dependencies clear
    it avoid null pointer exceptions
    and assures immutability since the service is final and injected only once
    at the time of class creation
     */
    @Autowired
    public SectionResolver(SectionService sectionService) {
        this.sectionService = sectionService;
    }


    // we need to explicit indicate that we want to validate the request body
    //works
    @GraphQLMutation(name = "createSection", description = "create a new section")
    public ApiResponse<Section> createSection(@Valid @GraphQLNonNull @GraphQLArgument SectionDetailsInput sectionDetailsInput) {
        System.out.println("createSectionMutationn" + sectionDetailsInput.toString());
        return  new ApiResponse<>(
                true,
                "Section created successfully",
                sectionService.createSection(sectionDetailsInput)
        );

    }


    //works
    @GraphQLQuery(description = "get section by id")
    public ApiResponse<Section> getSectionById(@GraphQLNonNull @GraphQLArgument Integer id) {
        System.out.println("getSectionById" + id);
        return new ApiResponse<Section>(
                true,
                "Section fetched successfully",
                sectionService.getSectionById(id)
        );

    }

    // get all sections graphql query
    //works
    @GraphQLQuery(description = "get all sections")
    public ApiResponse<List<Section>> getAllSections() {
        return new ApiResponse<List<Section>> (
                true,
                "Sections fetched successfully",
                sectionService.getAllSections()
        );
    }




    @GraphQLMutation(description = "update section details")
    public ApiResponse<Section> updateSectionDetails(
            @GraphQLNonNull @GraphQLArgument Integer id,
            @Valid @GraphQLNonNull @GraphQLArgument SectionDetailsInput sectionDetailsInput){
        return new ApiResponse<>(
                true,
            "Section updated successfully",
            sectionService.updateSectionDetails(id, sectionDetailsInput)
        );

    }


    @GraphQLQuery(description = "get section by name")
    public ApiResponse<Section> getSectionByName(@GraphQLNonNull @GraphQLArgument String name) {
        return new ApiResponse<>(
                true,
                "Section fetched successfully",
                sectionService.getSectionByName(name)
        );
    }

    @GraphQLMutation(description = "swap sections positions")
    public ApiResponse<List<Section>> swapSectionsPositions(@Valid @GraphQLNonNull @GraphQLArgument List<SectionPositionInput> sectionPositionInputList) {
        return new ApiResponse<>(
                true,
                "Sections positions updated successfully",
                sectionService.swapSectionsPositions(sectionPositionInputList)
        );
    }



    @GraphQLMutation(description = "sort sections by alphabetical order")
    public ApiResponse<List<Section>> sortSectionsByAlphabeticalOrder() {
        return new ApiResponse<>(
                true,
                "Sections sorted by alphabetical order",
                sectionService.sortSectionsByAlphabeticalOrder()
        );
    }

    // delete section
    //works
    @GraphQLMutation(description = "delete section by id")
    public ApiResponse<Boolean> deleteSection(@GraphQLNonNull @GraphQLArgument Integer id) {
        return new ApiResponse<Boolean>(
                true,
                "Section deleted successfully",
                sectionService.deleteSection(id)
        );
    }

}
