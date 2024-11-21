package com.mas.e_commerce_back.repositories;

import com.mas.e_commerce_back.entities.Category;
import com.mas.e_commerce_back.entities.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM product_images WHERE product_id = :productId ORDER BY position ASC;")
    List<ProductImage> findAllByProductId(@Param("productId") Integer productId);

    @Query(nativeQuery = true, value = "SELECT * FROM product_images WHERE position = (SELECT MAX(position) FROM product_images WHERE product_id = :productId) AND section_id = :productId")
    Optional<Category> findByLastPositionAndProductId(@Param("productId") Integer productId);


    @Query(nativeQuery = true, value = "SELECT * FROM product_images WHERE id IN (:ids) AND product_id = :productId")
    List<ProductImage> findAllByIdsAndProductId(Set<Integer> ids, Integer productId);
}
