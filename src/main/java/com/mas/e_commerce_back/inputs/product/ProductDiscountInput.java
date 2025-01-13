package com.mas.e_commerce_back.inputs.product;

import io.leangen.graphql.annotations.types.GraphQLType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@GraphQLType
public class ProductDiscountInput {

    @NotNull(message = "Invalid Product Id: Null value provided")
    private Integer productId;

    @Positive
    @NotNull
    private BigDecimal discountPrice;

    @NotNull
    private ZonedDateTime discountStartDate;

    @NotNull
    private ZonedDateTime discountEndDate;


}
