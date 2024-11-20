package com.mas.e_commerce_back.repositories;

import com.mas.e_commerce_back.entities.Category;
import com.mas.e_commerce_back.entities.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM product_types where category_id = :id")
    List<ProductType> getAllByCategoryId(@Param("id") Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM product_types where category_id = :categoryId ORDER BY name ASC ;")
    List<Category> findAllByCategoryIdOrderByName(@Param("categoryId") Integer categoryId);

    @Query(nativeQuery = true, value = "SELECT * FROM product_types WHERE name = :name")
    ProductType findByName(@Param("name") String name);

    @Query(nativeQuery = true, value = "SELECT * FROM product_types WHERE position = (SELECT MAX(position) FROM product_types WHERE category_id = :categoryId) AND category_id = :categoryId")
    ProductType findProductTypeByLastPositionAndCategoryId(@Param("categoryId") Integer categoryId);

    @Query(nativeQuery = true, value = "SELECT EXISTS (SELECT 1 FROM product_types WHERE name = :productTypeName);")
    boolean existsByName(@Param("productTypeName") String productTypeName);

    @Query(nativeQuery = true, value = "SELECT EXISTS (SELECT 1 FROM product_types WHERE product_type_id = :id);")
    boolean existsById(@Param("id") Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM product_types WHERE product_type_id IN (:ids) ORDER BY position ASC;")
    List<ProductType> findByIdsOrderByPosition(@Param("ids") Set<Integer> ids);


    @Query(nativeQuery = true, value = "SELECT * FROM product_types WHERE category_id = :categoryId ORDER BY position ASC;")
    List<ProductType> findAllByCategoryIdOrderByPosition(@Param("categoryId") Integer categoryId);

}
