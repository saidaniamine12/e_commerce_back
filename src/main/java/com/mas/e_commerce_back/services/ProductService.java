package com.mas.e_commerce_back.services;

import com.mas.e_commerce_back.entities.Product;
import com.mas.e_commerce_back.inputs.ProductInput;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product createProduct(ProductInput productInput);

    Product getProductById(Integer id);

    Product updateProductById(Integer id, ProductInput productInput);

    Product deleteProductById(Integer id);

    boolean productExists(Integer id);

    Product getProductByName(String name);

    Product getProductListByProductTypeName(String productTypeName);

    Product getProductListByProductTypeId(String productTypeId);


    List<Product> getProductListByCategoryName(String categoryName);

    List<Product> getProductListByCategoryId(Integer categoryId);



}
