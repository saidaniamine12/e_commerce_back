package com.mas.e_commerce_back.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "manufacturers")
@AllArgsConstructor
@NoArgsConstructor
public class Manufacturer {

    @Id
    @Column(name = "manufacturer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer manufacturerId;

    @NotBlank( message = "Invalid Name: Empty or Null value provided")
    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String imageUrl;

    @OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY)
    private List<Product> productList;


}
