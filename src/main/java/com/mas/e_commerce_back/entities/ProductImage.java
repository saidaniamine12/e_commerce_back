package com.mas.e_commerce_back.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@Table(name = "product_images")
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {
    @Id
    @Column(name = "product_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productImageId;

    @NotBlank( message = "Invalid Name: Empty or Null value provided")
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String imageUrl;

    @Min(value = 0,message = "Image order index must be at least 0")
    @Max(value = 9, message = "Image order index cannot exceed 9")
    private Integer position;

    @ManyToOne
    @JoinColumn(name = "product_id",  foreignKey = @ForeignKey(name = "fk_product_image_product"))
    private Product product;

}
