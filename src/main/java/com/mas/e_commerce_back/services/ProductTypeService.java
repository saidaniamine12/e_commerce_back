package com.mas.e_commerce_back.services;

import com.mas.e_commerce_back.entities.ProductType;
import com.mas.e_commerce_back.inputs.ProductTypeInput;

import java.util.List;

public interface ProductTypeService {

     List<ProductType> getProductTypesByCategoryId(Integer categoryId);

     ProductType getProductTypeById(Integer id);

     ProductType getProductTypeByName(String name);

     ProductType createProductType(ProductTypeInput productTypeInput);

     ProductType updateProductTypeDetails(Integer id, ProductTypeInput productTypeInput);

     // swap positions of product types
     List<ProductType> swapProductTypesPositions(List<ProductTypeInput> productTypeInput);

     // set the default positions of a product types list by their alphabetical order
     List<ProductType> sortProductTypesByAlphabeticalOrder(Integer categoryId);

     List<ProductType> getProductTypesByCategoryIdAndName(Integer categoryId, String name);

     Boolean deleteProductType(Integer id);







}
