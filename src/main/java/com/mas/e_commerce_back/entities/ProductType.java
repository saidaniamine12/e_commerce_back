package com.mas.e_commerce_back.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@Table(name = "product_types")
@AllArgsConstructor
@NoArgsConstructor
public class ProductType {

    @Id
    @Column(name = "product_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productTypeId;

    @NotBlank( message = "Invalid Name: Empty or Null value provided")
    @Column(nullable = false, unique = true)
    private String name;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", foreignKey = @ForeignKey(name = "fk_categories_product_types"))
    private Category category;

    @OneToMany(mappedBy = "productType", fetch = FetchType.LAZY)
    private List<Product> productList;


    @Column(columnDefinition = "TEXT")
    private String techSpecKeys;

    @Column(nullable = false)
    private Integer position;

    public void setTechSpecKeys(List<String> keys) {
        this.techSpecKeys = String.join("," , keys);
    }

}



