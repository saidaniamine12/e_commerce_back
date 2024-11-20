package com.mas.e_commerce_back.inputs;


import io.leangen.graphql.annotations.types.GraphQLType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@GraphQLType
public class ProductImageInput {

        @NotNull
        private Integer productId;

        @NotNull
        private MultipartFile image;

        @NotNull
        private Number imageOrderIndex;




}
