package com.mas.e_commerce_back.dtos;

import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@GraphQLType
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

}

