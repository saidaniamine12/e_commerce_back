package com.mas.e_commerce_back.inputs;

import io.leangen.graphql.annotations.types.GraphQLType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@GraphQLType
public class ManufacturerInput {

    private Integer manufacturerId;

    @NotBlank( message = "Invalid Name: Empty or Null value provided")
    private String name;


    private MultipartFile logo;

}
