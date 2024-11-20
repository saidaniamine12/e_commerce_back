package com.mas.e_commerce_back.inputs;

import com.fasterxml.jackson.databind.JsonNode;
import io.leangen.graphql.annotations.types.GraphQLType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public class ProductInput {

    @NotBlank( message = "Invalid Name: Empty or Null value provided")
    private String name;

    private String description;

    @Positive
    private BigDecimal price;

    private Boolean isDiscounted;

    private ProductDiscountInput discount;

    private String warranty;

    private Integer productTypeId;

    private String gtin;

    private String sku;

    @Min(0)
    private Integer amountToAdd;


    private JsonNode techSpecValues;


}
