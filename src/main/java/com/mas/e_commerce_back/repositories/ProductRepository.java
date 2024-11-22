package com.mas.e_commerce_back.repositories;

import com.mas.e_commerce_back.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{


    @Query(nativeQuery = true, value = "SELECT * FROM products WHERE slug = :slug")
    Optional<Product> findBySlug(@Param("slug") String slug);

    // exists by name
    @Query(nativeQuery = true, value = "SELECT EXISTS(SELECT 1 FROM products WHERE name = :name)")
    boolean existsByName(@Param("name") String name);

    // exists by slug
    @Query(nativeQuery = true, value = "SELECT EXISTS(SELECT 1 FROM products WHERE slug = :slug)")
    boolean existsBySlug(@Param("slug") String slug);


    @Query(nativeQuery = true, value = "SELECT * FROM products WHERE is_visible = false")
    List<Product> findAllWhereNotIsVisible();

    // discounted products
    @Query(nativeQuery = true, value = "SELECT * FROM products WHERE is_discounted = true")
    List<Product> findAllDiscountedProducts();

    @Query(nativeQuery = true, value = "SELECT * FROM products WHERE product_type_id = :productTypeId")
    List<Product> findAllByProductTypeId(@Param("productTypeId") String productTypeId);

    @Query(nativeQuery = true ,value = "SELECT p.* FROM product p " +
            "JOIN product_type pt ON p.product_type_id = pt.id " +
            "JOIN category c ON pt.category_id = c.id " +
            "WHERE c.id = :categoryId")
    List<Product> findAllByProductTypeCategoryId(Integer categoryId);
}
