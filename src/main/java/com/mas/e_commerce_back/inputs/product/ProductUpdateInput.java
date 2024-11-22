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
public class ProductUpdateInput {

    @NotNull( message = "Invalid ProductId: Null value provided")
    private Integer productId;

    private Integer ManufacturerId;

    private Integer productTypeId;

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
