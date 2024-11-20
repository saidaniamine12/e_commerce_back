package com.mas.e_commerce_back.inputs;

import io.leangen.graphql.annotations.types.GraphQLType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@GraphQLType
public class CategoryPositionInput {

    @NotNull(message = "Invalid category Id: Null value provided")
    private Integer positionId;

    @NotNull(message = "Invalid category Id: Null value provided")
    private Integer categoryId;

    @NotNull(message = "Invalid Position: Null value provided")
    @Min(value = 0, message = "Invalid Position: Position should be greater than or equal to 0")
    private Integer position;

}
