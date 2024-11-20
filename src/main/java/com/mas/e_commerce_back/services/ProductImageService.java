package com.mas.e_commerce_back.services;

import com.mas.e_commerce_back.entities.ProductImage;
import com.mas.e_commerce_back.inputs.ProductImageInput;

import java.util.List;

public interface ProductImageService {

    ProductImage saveProductImageList(List<ProductImageInput> productImageInputList);

    ProductImage updateProductImageById(Integer id, ProductImageInput productImage);

    ProductImage getProductImageById(Integer id);

    List<ProductImage> getAllProductImagesByProductId(Integer productId);

    List<ProductImage> getAllProductImagesByProductName(String productName);

    List<ProductImage> deleteProductImageListByProductId(Integer productId, List<Integer> ids);

}
