package com.mas.e_commerce_back.entities;

import com.fasterxml.jackson.databind.JsonNode;
import io.hypersistence.utils.hibernate.type.json.JsonNodeBinaryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Data
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @ManyToOne
    @JoinColumn(name = "product_type_id", nullable = false,  foreignKey = @ForeignKey(name = "fk_products_product_types"))
    private ProductType productType;

    @NotBlank( message = "Invalid Name: Empty or Null value provided")
    @Column(unique = true, nullable = false)
    private String name;

    private String thumbnail;

    @NotNull
    @NotEmpty
    @Column(unique = true, nullable = false)
    private String slug;

    @NotNull
    @NotEmpty
    @Column(unique = true)
    // this attribute defines the reference of each product (Stock keeping unit)
    private String sku;

    // global trade number item, bar code
    @Column(unique = true)
    private String gtin;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> imageList;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    @NotEmpty
    @Column(precision = 10, scale = 3)
    private BigDecimal price;

    @Column(precision = 10, scale = 3)
    private BigDecimal discountAmount;

    @Column(precision = 10, scale = 3)
    private BigDecimal discountPrice;

    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime discountStartDate;

    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime discountEndDate;

    @Column(name = "is_discounted",nullable = false)
    private boolean isDiscounted = false ;

    private Number stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id",nullable = false,  foreignKey = @ForeignKey(name = "fk_product_manufacturer"))
    private Manufacturer manufacturer;

    @Column(columnDefinition = "jsonb")
    @Type(JsonNodeBinaryType.class)
    private JsonNode techSpecValues;

    private String warranty;

    @CreationTimestamp
    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime addedAt;

    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @Column(name = "is_visible",nullable = false)
    private boolean isVisible = false ;

    public static String generateSlug(String productName) {
        String[] nameArr = productName.toLowerCase().split(" ");
        StringBuilder slugBuilder = new StringBuilder();
        for (String s : nameArr) {
            // Remove special characters from `s`
            s = s.replaceAll("[^a-z0-9-]", "");

            if (s.equals("-") || s.isEmpty()) continue; // Skip unwanted cases
            slugBuilder.append(s).append("-");
        }
        slugBuilder = slugBuilder.deleteCharAt(slugBuilder.length() - 1);
        return slugBuilder.toString();

    }


}
