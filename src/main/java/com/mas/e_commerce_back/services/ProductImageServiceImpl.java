package com.mas.e_commerce_back.services;

import com.mas.e_commerce_back.entities.Product;
import com.mas.e_commerce_back.entities.ProductImage;
import com.mas.e_commerce_back.inputs.DeleteProductImageInput;
import com.mas.e_commerce_back.inputs.ProductImageInput;
import com.mas.e_commerce_back.inputs.ProductImagePositionInput;
import com.mas.e_commerce_back.repositories.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class ProductImageServiceImpl implements ProductImageService{

    private final ProductImageRepository productImageRepository;
    private final ProductService productService;

    // get all the allowed extensions

    @Autowired
    public ProductImageServiceImpl(ProductImageRepository productImageRepository, ProductService productService) {
        this.productImageRepository = productImageRepository;
        this.productService = productService;
    }

    @Override
    public List<ProductImage> saveProductImageList(List<ProductImageInput> productImageInputList) {
        Integer productId = productImageInputList.get(0).getProductId();
        productImageInputList.forEach(productImageInput -> {
            if (productImageInput.getProductId() != productId) {
                throw new IllegalArgumentException("Product id must be the same for all images");
            }
        });

        Product product = productService.getProductById(productId);

        // get the image with the highest position and increment it by 1
        AtomicReference<Integer> position = new AtomicReference<>(0);
        productImageRepository.findByLastPositionAndProductId(productId).ifPresent(
                productImage -> {
                    position.set(productImage.getPosition() + 1);
                }
        );

        List<ProductImage> productImages = new ArrayList<>();
        for (int i = 0; i < productImageInputList.size(); i++) {
            MultipartFile multipartFile = productImageInputList.get(i).getImageMultipartFile();
            Integer lastDotIndex = multipartFile.getOriginalFilename().lastIndexOf(".");
            if (lastDotIndex == -1) {
                throw new IllegalArgumentException("Invalid file name");
            }
            String imageName = multipartFile.getOriginalFilename().substring(0, lastDotIndex)
                    + "-"
                    + position.get()
                    + multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            ProductImage productImage = ProductImage.builder()
                    .product(product)
                    .name(imageName)
                    .position(position.get())
                   // TODO: .imageUrl()
                    .build();
            position.set(position.get() + 1);
        }
        return productImageRepository.saveAll(productImages);

    }

    @Override
    public List<ProductImage> updateProductImageListPosition(List<ProductImagePositionInput> productImagePositionInputList) {
        Set<Integer> positions = productImagePositionInputList.stream().map(ProductImagePositionInput::getPosition).collect(Collectors.toSet());
        Set<Integer> ids = productImagePositionInputList.stream().map(ProductImagePositionInput::getImageId).collect(Collectors.toSet());

        if (ids.size() != productImagePositionInputList.size()) {
            throw new IllegalArgumentException("Cannot use the same image ID twice within the swapping list");
        }
        if (positions.size() != productImagePositionInputList.size()) {
            throw new IllegalArgumentException("Cannot use the same position twice within the swapping list");
        }

        List<ProductImage> productImageList = productImageRepository.findAllById(ids);
        Integer productId = productImageList.get(0).getProduct().getProductId();
        if (productId == null) {
            throw new IllegalArgumentException("The product with id " + productId + " does not exist in the database");
        }

        // Check if the product image list is within the same product id
        productImageList.forEach(productImage -> {
            if (!productImage.getProduct().getProductId().equals(productId)) {
                throw new IllegalArgumentException("The product image with id " + productImage.getProductImageId()
                        + " is not in the same product as the other images, all images must be in the same product");
            }
    });

        // Swap the positions
        for (ProductImagePositionInput productImagePositionInput : productImagePositionInputList) {
            ProductImage productImage = productImageList.stream()
                    .filter(image -> image.getProductImageId().equals(productImagePositionInput.getImageId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("The product image with id " + productImagePositionInput.getImageId() + " does not exist in the database"));
            productImage.setPosition(productImagePositionInput.getPosition());

        }

        productImageRepository.saveAll(productImageList);

        return productImageRepository.findAllByProductId(productId);
    }

    @Override
    public ProductImage getProductImageById(Integer id) {
        return productImageRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product image with id " + id + " not found")
        );
    }

    @Override
    public List<ProductImage> getAllProductImagesByProductId(Integer productId) {
        return productImageRepository.findAllByProductId(productId);
    }

    @Override
    public Boolean deleteProductImageListByProductId(List<DeleteProductImageInput> deleteProductImageInputList) {
        Set<Integer> ids = deleteProductImageInputList.stream().map(DeleteProductImageInput::getImageId).collect(Collectors.toSet());
        if (ids.size() != deleteProductImageInputList.size()) {
            throw new IllegalArgumentException("Cannot use the same image ID twice within the deletion list");
        }

        Integer productId = deleteProductImageInputList.get(0).getProductId();
        deleteProductImageInputList.forEach(deleteProductImageInput -> {
            if (deleteProductImageInput.getProductId() != productId) {
                throw new IllegalArgumentException("Product id must be the same for all images");
            }
        });

        // check for product
        Product product = productService.getProductById(productId);
        List<ProductImage> productImageList = productImageRepository.findAllByIdsAndProductId(ids, productId);
        if (productImageList.size() != ids.size()) {
            throw new IllegalArgumentException("Some images do not exist in the database");
        }

        productImageRepository.deleteAll(productImageList);



        return true;
    }


}
