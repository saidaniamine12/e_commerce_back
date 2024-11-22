package com.mas.e_commerce_back.services;


import com.mas.e_commerce_back.entities.Manufacturer;
import com.mas.e_commerce_back.entities.Product;
import com.mas.e_commerce_back.entities.ProductImage;
import com.mas.e_commerce_back.entities.ProductType;
import com.mas.e_commerce_back.exceptions.BadRequestException;
import com.mas.e_commerce_back.exceptions.NotFoundException;
import com.mas.e_commerce_back.inputs.product.ProductDiscountInput;
import com.mas.e_commerce_back.inputs.product.ProductCreateInput;
import com.mas.e_commerce_back.inputs.product.ProductUpdateInput;
import com.mas.e_commerce_back.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    private final ManufacturerService manufacturerService;

    private final ProductTypeService productTypeService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ManufacturerService manufacturerService, ProductTypeService productTypeService) {
        this.productRepository = productRepository;
        this.manufacturerService = manufacturerService;
        this.productTypeService = productTypeService;
    }

    @Override
    public Product createProduct(ProductCreateInput productInput) {

        // check manufacturer exists
        Manufacturer manufacturer = manufacturerService.getManufacturerById(productInput.getManufacturerId());
        // check for product type
        ProductType productType = productTypeService.getProductTypeById(productInput.getProductTypeId());
        if (productRepository.existsByName(productInput.getName())) {
            throw new NotFoundException("Product with name " + productInput.getName() + " already exists");
        }

        // ensure the name starts only with letters
        if (!productInput.getName().matches("^[a-zA-Z].*")) {
            throw new BadRequestException("Product name must start with a letter");
        }

        // Generate slug
        String slug = Product.generateSlug(productInput.getName());
        if (productRepository.existsBySlug(slug)) {
            throw new DataIntegrityViolationException("Product with slug " + slug + " already exists");
        }

        Product product = Product.builder()
                .manufacturer(manufacturer)
                .productType(productType)
                .name(productInput.getName())
                .slug(slug)
                .description(productInput.getDescription())
                .warranty(productInput.getWarranty())
                .gtin(productInput.getGtin())
                .sku(productInput.getSku())
                .techSpecValues(productInput.getTechSpecValues())
                .price(productInput.getPrice())
                .stockQuantity(productInput.getStock())
                .build();
        return productRepository.save(product);

    }


    @Override
    public Product applyDiscountToProduct(ProductDiscountInput productDiscountInput) {
        Product product = productRepository.findById(productDiscountInput.getProductId()).orElseThrow(
                () -> new NotFoundException("no product found with id:" + productDiscountInput.getProductId())
        );

        // check if the discount is valid
        if (productDiscountInput.getDiscountPrice().compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw new IllegalArgumentException("Discount price must be greater than 0");
        }

        // check if  price is greater than discount price
        if (product.getPrice().compareTo(productDiscountInput.getDiscountPrice()) <= 0) {
            throw new IllegalArgumentException("Discount price must be less than the product price");
        }

        // check discount dates
        if (productDiscountInput.getDiscountStartDate().isAfter(productDiscountInput.getDiscountEndDate())) {
            throw new IllegalArgumentException("Start date must be before the end date");
        }


        product.setDiscountPrice(productDiscountInput.getDiscountPrice());
        product.setDiscountStartDate(productDiscountInput.getDiscountStartDate());
        product.setDiscountEndDate(productDiscountInput.getDiscountEndDate());
        product.setDiscounted(true);
        return productRepository.save(product);

    }


    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElseThrow(
                () -> new NotFoundException("no product found with id:" + id))
        ;
    }


    // TODO: Implement this method using filters
    @Override
    public List<Product> getAllProducts() {
        return null;
    }


    @Override
    public Product updateProduct(ProductUpdateInput productInput) {
        Product product = productRepository.findById(productInput.getProductId()).orElseThrow(
                () -> new NotFoundException("no product found with id:" + productInput.getProductId() + "found to update")
        );
        // name + slug
        if (productInput.getName() != null) {
            if (productRepository.existsByName(productInput.getName())) {
                throw new NotFoundException("Product with name " + productInput.getName() + " already exists");
            }
            // ensure the name starts only with letters
            if (!productInput.getName().matches("^[a-zA-Z].*")) {
                throw new BadRequestException("Product name must start with a letter");
            }
            String slug = Product.generateSlug(productInput.getName());
            if (productRepository.existsBySlug(slug)) {
                throw new DataIntegrityViolationException("Product with slug " + slug + " already exists");
            }
            product.setName(productInput.getName());
            product.setSlug(slug);
        }

        // manufacturer
        if (productInput.getManufacturerId() != null) {
            Manufacturer manufacturer = manufacturerService.getManufacturerById(productInput.getManufacturerId());
            product.setManufacturer(manufacturer);
        }

        // product type
        if (productInput.getProductTypeId() != null) {
            ProductType productType = productTypeService.getProductTypeById(productInput.getProductTypeId());
            product.setProductType(productType);
        }

        // description
        if (productInput.getDescription() != null) {
            product.setDescription(productInput.getDescription());
        }

        // price
        if (productInput.getPrice() != null) {
            product.setPrice(productInput.getPrice());
        }

        //  warranty
        if (productInput.getWarranty() != null) {
            product.setWarranty(productInput.getWarranty());
        }

        // gtin
        if (productInput.getGtin() != null) {
            product.setGtin(productInput.getGtin());
        }

        // sku
        if (productInput.getSku() != null) {
            product.setSku(productInput.getSku());
        }

        // stock
        if (productInput.getStock() != null) {
            product.setStockQuantity(productInput.getStock());
        }

        // tech spec values
        if (productInput.getTechSpecValues() != null) {
            product.setTechSpecValues(productInput.getTechSpecValues());
        }

        return productRepository.save(product);
    }

    @Override
    public Boolean deleteProductById(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NotFoundException("no product found with id:" + id)
        );
        productRepository.delete(product);
        return true;
    }


    @Override
    public Product getProductBySlug(String slug) {
        return productRepository.findBySlug(slug).orElseThrow(
                () -> new NotFoundException("no product found with slug:" + slug)
        );
    }

    @Override
    public List<Product> getProductListByProductTypeId(String productTypeId) {
        return productRepository.findAllByProductTypeId(productTypeId);
    }

    @Override
    public List<Product> getProductListByCategoryId(Integer categoryId) {
        return productRepository.findAllByProductTypeCategoryId(categoryId);
    }

    @Override
    public List<Product> getAllInvisibleProducts() {
        return productRepository.findAllWhereNotIsVisible();
    }

    @Override
    public void setThumbnailImage(Integer productId, Integer productImageId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NotFoundException("Product with id " + productId + " not found")
        );

        ProductImage productImage = product.getImageList().stream()
                .filter(image -> image.getProductImageId().equals(productImageId))
                .findFirst()
                .orElseThrow(
                        () -> new NotFoundException("Product image with id " + productImageId + " not found")
                );
        product.setThumbnail(productImage.getImageUrl());
        productRepository.save(product);
    }

}
