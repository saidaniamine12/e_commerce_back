package com.mas.e_commerce_back.services;


import com.mas.e_commerce_back.entities.Product;
import com.mas.e_commerce_back.exceptions.NotFoundException;
import com.mas.e_commerce_back.inputs.ProductInput;
import com.mas.e_commerce_back.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Product createProduct(ProductInput productInput) {
        return null;
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElseThrow(
                () -> new NotFoundException("no product found with id:" + id))
        ;
    }

    @Override
    public Product updateProductById(Integer id, ProductInput productInput) {
        return null;
    }

    @Override
    public Product deleteProductById(Integer id) {
        return null;
    }

    @Override
    public boolean productExists(Integer id) {
        return false;
    }

    @Override
    public Product getProductByName(String name) {
        return null;
    }

    @Override
    public Product getProductListByProductTypeName(String productTypeName) {
        return null;
    }

    @Override
    public Product getProductListByProductTypeId(String productTypeId) {
        return null;
    }

    @Override
    public List<Product> getProductListByCategoryName(String categoryName) {
        return null;
    }

    @Override
    public List<Product> getProductListByCategoryId(Integer categoryId) {
        return null;
    }
}
