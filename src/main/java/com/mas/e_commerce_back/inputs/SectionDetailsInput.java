package com.mas.e_commerce_back.inputs;

import io.leangen.graphql.annotations.types.GraphQLType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@GraphQLType
public class SectionDetailsInput {

    @NotBlank( message = "Invalid Name: Empty value provided")
    private String name;

    private String description;
}
