package com.mas.e_commerce_back.services;

import com.mas.e_commerce_back.entities.ProductType;
import com.mas.e_commerce_back.inputs.ProductTypeInput;
import com.mas.e_commerce_back.inputs.ProductTypePositionInput;

import java.util.List;

public interface ProductTypeService {

     List<ProductType> getAllProductTypesByCategoryId(Integer categoryId);

     ProductType getProductTypeById(Integer id);

     ProductType getProductTypeByName(String name);

     ProductType createProductType(ProductTypeInput productTypeInput);

     ProductType updateProductTypeDetails(ProductTypeInput productTypeInput);

     // swap positions of product types
     List<ProductType> swapProductTypesPositions(List<ProductTypePositionInput> categoryPositionInputList);

     // set the default positions of a product types list by their alphabetical order
     List<ProductType> sortProductTypesByAlphabeticalOrder(Integer categoryId);


     Boolean deleteProductType(Integer id);







}
