package com.mas.e_commerce_back.services;

import com.mas.e_commerce_back.entities.ProductImage;
import com.mas.e_commerce_back.inputs.DeleteProductImageInput;
import com.mas.e_commerce_back.inputs.ProductImageInput;
import com.mas.e_commerce_back.inputs.ProductImagePositionInput;

import java.util.List;

public interface ProductImageService {

    List<ProductImage> saveProductImageList(List<ProductImageInput> productImageInputList);

    List<ProductImage> updateProductImageListPosition(List<ProductImagePositionInput> productImagePositionInput);

    ProductImage getProductImageById(Integer id);

    List<ProductImage> getAllProductImagesByProductId(Integer productId);
    
    Boolean deleteProductImageListByProductId(List<DeleteProductImageInput> deleteProductImageInputList);

}
