//package com.mas.e_commerce_back.controllers;
//
//
//import com.mas.e_commerce_back.dtos.ApiResponse;
//import com.mas.e_commerce_back.entities.ProductImage;
//import com.mas.e_commerce_back.inputs.ProductImageInput;
//import com.mas.e_commerce_back.services.ProductImageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("api/v1/product-images")
//public class ProductImageController {
//
//    private final ProductImageService productImageService;
//
//
//    @Autowired
//    public ProductImageController(ProductImageService productImageService) {
//        this.productImageService = productImageService;
//    }
//
//    @PostMapping("/new")
//    public ResponseEntity<ApiResponse<ProductImage>> saveProductImage(@RequestBody List<ProductImageInput> productImageInput) {
//
//            final ApiResponse<ProductImage> response = new ApiResponse<>(
//                    true,
//                    "product image saved successfully",
//                    productImageService.saveProductImageList(productImageInput)
//            );
//            return ResponseEntity.ok(response);
//    }
//
//    @PutMapping("/id/{id}")
//    public ResponseEntity<ApiResponse<ProductImage>> updateProductImageById(@PathVariable("id") Integer id, @RequestBody ProductImageInput productImageInput) {
//
//        final ApiResponse<ProductImage> response = new ApiResponse<>(
//                true,
//                "product image updated successfully",
//                productImageService.updateProductImageById(id, productImageInput)
//        );
//        return ResponseEntity.ok(response);
//    }
//
//
//    @GetMapping("/id/{id}")
//    public ResponseEntity<ApiResponse<ProductImage>> getProductImageById(@PathVariable("id") Integer id) {
//
//        final ApiResponse<ProductImage> response = new ApiResponse<>(
//                true,
//                "product image retrieved successfully",
//                productImageService.getProductImageById(id)
//        );
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/products/id/{id}")
//    public ResponseEntity<ApiResponse<List<ProductImage>>> getAllProductImagesByProductId(@PathVariable("id") Integer id) {
//
//        final ApiResponse<List<ProductImage>> response = new ApiResponse<>(
//                true,
//                "product images retrieved successfully",
//                productImageService.getAllProductImagesByProductId(id)
//        );
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/products/name/{name}")
//    public ResponseEntity<ApiResponse<List<ProductImage>>> getAllProductImagesByProductName(@PathVariable("name") String name) {
//
//        final ApiResponse<List<ProductImage>> response = new ApiResponse<>(
//                true,
//                "product images retrieved successfully",
//                productImageService.getAllProductImagesByProductName(name)
//        );
//        return ResponseEntity.ok(response);
//    }
//
//    @DeleteMapping("/products/{id}")
//    public ResponseEntity<ApiResponse<List<ProductImage>>> deleteProductImageListByProductId(@PathVariable("id") Integer productId,
//                                                                                             @RequestBody List<Integer> ids) {
//
//        final ApiResponse<List<ProductImage>> response = new ApiResponse<>(
//                true,
//                "product images deleted successfully",
//                productImageService.deleteProductImageListByProductId(productId, ids)
//        );
//        return ResponseEntity.ok(response);
//    }
//
//
//
//
//
//
//
//
//}
