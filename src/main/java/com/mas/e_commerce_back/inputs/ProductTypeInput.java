package com.mas.e_commerce_back.inputs;


import io.leangen.graphql.annotations.types.GraphQLType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@GraphQLType
public class ProductTypeInput {

    private Integer productTypeId;

    @NotBlank( message = "Invalid Name: Empty or Null value provided")
    private String name;

    private String description;

    @NonNull
    private Integer categoryId;


}
