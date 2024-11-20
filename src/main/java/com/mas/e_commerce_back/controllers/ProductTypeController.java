//package com.mas.e_commerce_back.controllers;
//
//
//import com.mas.e_commerce_back.dtos.ApiResponse;
//import com.mas.e_commerce_back.entities.ProductType;
//import com.mas.e_commerce_back.inputs.ProductTypeInput;
//import com.mas.e_commerce_back.services.ProductTypeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("api/v1/product-types")
//public class ProductTypeController {
//
//    private final ProductTypeService productTypeService;
//
//    @Autowired
//    public ProductTypeController(ProductTypeService productTypeService) {
//        this.productTypeService = productTypeService;
//    }
//
//
//    @PostMapping("/new")
//    public ResponseEntity<ApiResponse<ProductType>> createProductType(ProductTypeInput productTypeInput) {
//        final ApiResponse<ProductType> response = new ApiResponse<>(
//            true,
//            "Product type created successfully",
//            productTypeService.createProductType(productTypeInput)
//        );
//        return ResponseEntity.ok(response);
//    }
//
//    @PutMapping("/id/{id}")
//    public ResponseEntity<ApiResponse<ProductType>> updateProductTypeById(@PathVariable("id") Integer id, ProductTypeInput productTypeInput) {
//        final ApiResponse<ProductType> response = new ApiResponse<>(
//            true,
//            "Product type updated successfully",
//            productTypeService.updateProductType(id, productTypeInput)
//        );
//        return ResponseEntity.ok(response);
//    }
//
//    @DeleteMapping("/id/{id}")
//    public ResponseEntity<ApiResponse<ProductType>> deleteProductTypeById(@PathVariable("id") Integer id) {
//        final ApiResponse<ProductType> response = new ApiResponse<>(
//            true,
//            "Product type deleted successfully",
//            productTypeService.deleteProductType(id)
//        );
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/id/{id}")
//    public ResponseEntity<ApiResponse<ProductType>> getProductTypeById(@PathVariable("id") Integer id) {
//        final ApiResponse<ProductType> response = new ApiResponse<>(
//                true,
//                "Product type fetched successfully",
//                productTypeService.getProductTypeById(id)
//        );
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping()
//    public ResponseEntity<ApiResponse<List<ProductType>>> getAllProductTypes() {
//        final ApiResponse<List<ProductType>> response = new ApiResponse<>(
//                true,
//                "Product types fetched successfully",
//                productTypeService.getAllProductTypes()
//        );
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/categories/id/{categoryId}")
//    public ResponseEntity<ApiResponse<List<ProductType>>> getAllProductTypesByCategoryId(@PathVariable("categoryId") Integer categoryId) {
//        final ApiResponse<List<ProductType>> response = new ApiResponse<>(
//                true,
//                "Product types fetched successfully",
//                productTypeService.getAllProductTypesByCategoryId(categoryId)
//        );
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/categories/name/{categoryName}")
//    public ResponseEntity<ApiResponse<List<ProductType>>> getAllProductTypesByCategoryName(@PathVariable("categoryName") String categoryName) {
//        final ApiResponse<List<ProductType>> response = new ApiResponse<>(
//                true,
//                "Product types fetched successfully",
//                productTypeService.getAllProductTypesByCategoryName(categoryName)
//        );
//        return ResponseEntity.ok(response);
//    }
//
//
//}
