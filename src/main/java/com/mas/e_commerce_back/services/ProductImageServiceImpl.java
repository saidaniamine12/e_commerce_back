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
    public List<ProductImage> addProductImageList(List<ProductImageInput> productImageInputList) {
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
        String imageBaseName = product.getSlug() + "-";
        List<ProductImage> productImages = new ArrayList<>();
        for (int i = 0; i < productImageInputList.size(); i++) {
            MultipartFile multipartFile = productImageInputList.get(i).getImageMultipartFile();
            Integer lastDotIndex = multipartFile.getOriginalFilename().lastIndexOf(".");
            if (lastDotIndex == -1) {
                throw new IllegalArgumentException("Invalid file name");
            }
            String imageName = imageBaseName
                    + position.get()
                    + multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            ProductImage productImage = ProductImage.builder()
                    .product(product)
                    .position(position.get())
                   // TODO: .imageUrl()
                    .build();
            productImages.add(productImage);
            position.set(position.get() + 1);

        }

        productImageRepository.saveAll(productImages);

        // Fetch all images in order by position
        List<ProductImage> productImageList = ensurePositionsFixed(productId);

        // Handle thumbnail setting
        if (product.getThumbnail() == null || !product.getThumbnail().equals(productImageList.get(0).getImageUrl())) {
            // Set the first image as thumbnail
            product.setThumbnail(productImageList.get(0).getImageUrl());
        }

        return productImageList;

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
        Product product = productImageList.get(0).getProduct();
        Integer productId = product.getProductId();
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


        List<ProductImage> imageList = ensurePositionsFixed(productId);
        // Handle thumbnail setting
        if (product.getThumbnail() == null || !product.getThumbnail().equals(imageList.get(0).getImageUrl())) {
            // Set the first image as thumbnail
            product.setThumbnail(imageList.get(0).getImageUrl());
        }
        return imageList;
    }

    @Override
    public ProductImage getProductImageById(Integer id) {
        return productImageRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product image with id " + id + " not found")
        );
    }

    @Override
    public List<ProductImage> getAllProductImagesByProductId(Integer productId) {
        return productImageRepository.findAllByProductIdOrderByPosition(productId);
    }

    @Override
    public List<ProductImage> deleteProductImageListByProductId(List<DeleteProductImageInput> deleteProductImageInputList) {
        Set<Integer> ids = deleteProductImageInputList.stream().map(DeleteProductImageInput::getImageId).collect(Collectors.toSet());

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

        // get them again to set the new positions
        List<ProductImage> imageList = ensurePositionsFixed(productId);

        if (product.getThumbnail() == null || !product.getThumbnail().equals(productImageList.get(0).getImageUrl())) {
            productService.setThumbnailImage(product.getProductId(), imageList.get(0).getProductImageId());
        }

        return imageList;
    }


    private List<ProductImage> ensurePositionsFixed(Integer productId) {
        // Fetch images ordered by current position
        List<ProductImage> productImageList = productImageRepository.findAllByProductIdOrderByPosition(productId);

        boolean positionsFixed = false;
        for (int i = 0; i < productImageList.size(); i++) {
            if (productImageList.get(i).getPosition() != i) {
                productImageList.get(i).setPosition(i);
                positionsFixed = true;
            }
        }

        // Save only if positions were updated
        if (positionsFixed) {
            productImageRepository.saveAll(productImageList);
        }

        return productImageList;
    }


}
