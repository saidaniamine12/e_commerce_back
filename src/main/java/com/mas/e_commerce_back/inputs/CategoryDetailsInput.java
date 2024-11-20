package com.mas.e_commerce_back.inputs;

import io.leangen.graphql.annotations.types.GraphQLType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@GraphQLType
public class CategoryDetailsInput {

    @NotBlank( message = "Invalid Name: Empty value provided")
    private String name;

    private String description;

    @NotNull
    private Integer sectionId;

}
