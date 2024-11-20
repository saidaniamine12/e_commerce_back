package com.mas.e_commerce_back.inputs;

import io.leangen.graphql.annotations.types.GraphQLType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@GraphQLType
public class ProductDiscountInput {

    @Positive
    @NotNull
    private BigDecimal discountPrice;

    @NotNull
    private Date discountStartDate;

    @NotNull
    private Date discountEndDate;


}
