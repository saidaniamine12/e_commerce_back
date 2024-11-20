//package com.mas.e_commerce_back.controllers;
//
//import com.mas.e_commerce_back.dtos.ApiResponse;
//import com.mas.e_commerce_back.entities.Product;
//import com.mas.e_commerce_back.inputs.ProductInput;
//import com.mas.e_commerce_back.services.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("api/v1/products")
//public class ProductController {
//
//    private final ProductService productService;
//
//    @Autowired
//    public ProductController(ProductService productService) {
//        this.productService = productService;
//    }
//
//    @GetMapping
//    public ResponseEntity<ApiResponse<List<Product>>> getProducts() {
//
//        final ApiResponse<List<Product>> response = new ApiResponse<>(
//                true,
//                "products retrieved successfully",
//                productService.getAllProducts()
//        );
//        return ResponseEntity.ok(response);
//    }
//
//    @PostMapping("/new")
//    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody ProductInput productInput) {
//        final ApiResponse<Product> response = new ApiResponse<>(
//            true,
//            "Product created successfully",
//            productService.createProduct(productInput)
//        );
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/id/{id}")
//    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable("id") Integer id) {
//
//        final ApiResponse<Product> response = new ApiResponse<>(
//                true,
//                "product retrieved successfully",
//                productService.getProductById(id)
//        );
//        return ResponseEntity.ok(response);
//    }
//
//    @PutMapping("/id/{id}")
//    public ResponseEntity<ApiResponse<Product>> updateProductById(@PathVariable("id") Integer id, @RequestBody ProductInput productInput) {
//        final ApiResponse<Product> response = new ApiResponse<>(
//            true,
//            "Product updated successfully",
//            productService.updateProductById(id, productInput)
//        );
//        return ResponseEntity.ok(response);
//    }
//
//    @DeleteMapping("/id/{id}")
//    public ResponseEntity<ApiResponse<Product>> deleteProductById(@PathVariable("id") Integer id) {
//        final ApiResponse<Product> response = new ApiResponse<>(
//            true,
//            "Product deleted successfully",
//            productService.deleteProductById(id)
//        );
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/name/{name}")
//    public ResponseEntity<ApiResponse<Product>> getProductByName(@PathVariable("name") String name) {
//
//        final ApiResponse<Product> response = new ApiResponse<>(
//                true,
//                "product retrieved successfully",
//                productService.getProductByName(name)
//        );
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/product-types/name/{productTypeName}")
//    public ResponseEntity<ApiResponse<Product>> getProductListByProductType(@PathVariable("productTypeName") String productTypeName) {
//
//        final ApiResponse<Product> response = new ApiResponse<>(
//                true,
//                "product retrieved successfully",
//                productService.getProductListByProductTypeName(productTypeName)
//        );
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/product-types/id/{productTypeId}")
//    public ResponseEntity<ApiResponse<Product>> getProductListByProductTypeId(@PathVariable("productTypeId") String productTypeId) {
//
//        final ApiResponse<Product> response = new ApiResponse<>(
//                true,
//                "product retrieved successfully",
//                productService.getProductListByProductTypeId(productTypeId)
//        );
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/categories/id/{categoryId}")
//    public ResponseEntity<ApiResponse<List<Product>>> getProductListByCategoryId(@PathVariable("categoryId") Integer categoryId) {
//
//            final ApiResponse<List<Product>> response = new ApiResponse<>(
//                    true,
//                    "products retrieved successfully",
//                    productService.getProductListByCategoryId(categoryId)
//            );
//            return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/categories/name/{categoryName}")
//    public ResponseEntity<ApiResponse<List<Product>>> getProductListByCategoryName(@PathVariable("categoryName") String categoryName) {
//
//            final ApiResponse<List<Product>> response = new ApiResponse<>(
//                    true,
//                    "products retrieved successfully",
//                    productService.getProductListByCategoryName(categoryName)
//            );
//            return ResponseEntity.ok(response);
//    }
//
//
//}
