package com.mas.e_commerce_back.config;

import graphql.schema.GraphQLScalarType;
import io.leangen.graphql.annotations.types.GraphQLType;

@GraphQLType
public class UploadScalar {

    public static final GraphQLScalarType INSTANCE = GraphQLScalarType.newScalar()
            .name("Upload")
            .coercing(new UploadCoercing()) // Use a coercing that maps the file properly
            .build();
}