package com.mas.e_commerce_back.services;

import com.mas.e_commerce_back.entities.ProductType;
import com.mas.e_commerce_back.inputs.ProductTypeInput;
import com.mas.e_commerce_back.repositories.ProductTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    private final CategoryService categoryService;
    private final ProductTypeRepository productTypeRepository;

    public ProductTypeServiceImpl(CategoryService categoryService, ProductTypeRepository productTypeRepository) {
        this.categoryService = categoryService;
        this.productTypeRepository = productTypeRepository;
    }


    @Override
    public List<ProductType> getProductTypesByCategoryId(Integer categoryId) {
        return null;
    }

    @Override
    public ProductType getProductTypeById(Integer id) {
        return null;
    }

    @Override
    public ProductType getProductTypeByName(String name) {
        return null;
    }

    @Override
    public ProductType createProductType(ProductTypeInput productTypeInput) {
        return null;
    }

    @Override
    public ProductType updateProductTypeDetails(Integer id, ProductTypeInput productTypeInput) {
        return null;
    }

    @Override
    public List<ProductType> swapProductTypesPositions(List<ProductTypeInput> productTypeInput) {
        return null;
    }

    @Override
    public List<ProductType> sortProductTypesByAlphabeticalOrder(Integer categoryId) {
        return null;
    }

    @Override
    public List<ProductType> getProductTypesByCategoryIdAndName(Integer categoryId, String name) {
        return null;
    }

    @Override
    public Boolean deleteProductType(Integer id) {
        return null;
    }
}
