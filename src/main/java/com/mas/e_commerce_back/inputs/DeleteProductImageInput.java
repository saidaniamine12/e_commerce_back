package com.mas.e_commerce_back.inputs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteProductImageInput {
    @NotNull(message = "Product Id cannot be null")
    private Integer productId;

    @NotNull(message = "Image Id cannot be null")
    private Integer imageId;
}
