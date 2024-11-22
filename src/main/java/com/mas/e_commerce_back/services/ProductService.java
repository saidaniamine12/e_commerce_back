package com.mas.e_commerce_back.services;

import com.mas.e_commerce_back.entities.Product;
import com.mas.e_commerce_back.entities.ProductImage;
import com.mas.e_commerce_back.inputs.product.ProductDiscountInput;
import com.mas.e_commerce_back.inputs.product.ProductCreateInput;
import com.mas.e_commerce_back.inputs.product.ProductUpdateInput;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product createProduct(ProductCreateInput productInput);

    Product applyDiscountToProduct(ProductDiscountInput productDiscountInput);

    Product getProductById(Integer id);

    Product updateProduct(ProductUpdateInput productInput);

    Boolean deleteProductById(Integer id);

    Product getProductBySlug(String slug);

    List<Product> getProductListByProductTypeId(String productTypeId);

    List<Product> getProductListByCategoryId(Integer categoryId);

    List<Product> getAllInvisibleProducts();

    void setThumbnailImage(Integer productId, Integer productImageId);

}
