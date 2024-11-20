package com.mas.e_commerce_back.services;

import com.mas.e_commerce_back.entities.ProductImage;
import com.mas.e_commerce_back.inputs.ProductImageInput;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService{


    @Override
    public ProductImage saveProductImageList(List<ProductImageInput> productImageInputList) {
        return null;
    }

    @Override
    public ProductImage updateProductImageById(Integer id, ProductImageInput productImage) {
        return null;
    }

    @Override
    public ProductImage getProductImageById(Integer id) {
        return null;
    }

    @Override
    public List<ProductImage> getAllProductImagesByProductId(Integer productId) {
        return null;
    }

    @Override
    public List<ProductImage> getAllProductImagesByProductName(String productName) {
        return null;
    }

    @Override
    public List<ProductImage> deleteProductImageListByProductId(Integer productId, List<Integer> ids) {
        return null;
    }
}
