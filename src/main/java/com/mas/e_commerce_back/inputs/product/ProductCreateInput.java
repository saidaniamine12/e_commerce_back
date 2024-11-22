package com.mas.e_commerce_back.inputs.product;

import com.fasterxml.jackson.databind.JsonNode;
import io.leangen.graphql.annotations.types.GraphQLType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


/* the product input class is defining the initial data that will be used to create a product
and the discount part is going to be optional,
* */

@Data
@NoArgsConstructor
@GraphQLType
public class ProductCreateInput {


    @NotNull( message = "Invalid ManufacturerId: Null value provided")
    private Integer ManufacturerId;

    @NotNull( message = "Invalid productTypeId: Null value provided")
    private Integer productTypeId;

    @NotBlank( message = "Invalid Name: Empty or Null value provided")
    private String name;

    private String description;

    @Positive
    private BigDecimal price;

    private String warranty;

    private String gtin;

    private String sku;

    private Integer stock;

    private JsonNode techSpecValues;

}
