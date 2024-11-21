package com.mas.e_commerce_back.inputs;


import com.mas.e_commerce_back.validation.ValidImageSize;
import io.leangen.graphql.annotations.types.GraphQLType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@GraphQLType
public class ProductImageInput {

        @NotNull(message = "Product Id cannot be null")
        private Integer productId;

        @NotNull
        @ValidImageSize(size = 2, message = "Image size must be less than 2MB")
        private MultipartFile imageMultipartFile;

}
