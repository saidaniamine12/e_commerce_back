package com.mas.e_commerce_back.repositories;

import com.mas.e_commerce_back.entities.Category;
import com.mas.e_commerce_back.entities.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM categories where section_id = :sectionId")
    List<Category> findAllBySectionId(@Param("sectionId") Integer sectionId);

    @Query(nativeQuery = true, value = "SELECT * FROM categories where section_id = :sectionId ORDER BY name ASC ;")
    List<Category> findAllBySectionIdOrderByName(@Param("sectionId") Integer sectionId);

    @Query(nativeQuery = true, value = "SELECT * FROM categories WHERE name = :name")
    Optional<Category> findByName(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM categories WHERE position = (SELECT MAX(position) FROM categories WHERE section_id = :sectionId) AND section_id = :sectionId")
    Optional<Category> findCategoryByLastPositionAndCategoryId(@Param("sectionId") Integer sectionId);

    @Query(nativeQuery = true, value = "SELECT EXISTS (SELECT 1 FROM categories WHERE name = :categoryName);")
    boolean existsByName(@Param("categoryName") String categoryName);

    @Query(nativeQuery = true, value = "SELECT EXISTS (SELECT 1 FROM categories WHERE category_id = :id);")
    boolean existsById(@Param("id") Integer id);

    @Query(nativeQuery = true, value = "SELECT EXISTS (SELECT 1 FROM product_types WHERE category_id = :categoryId);")
    boolean productTypeListExists(@Param("categoryId") Integer categoryId);

    @Query(nativeQuery = true, value = "SELECT * FROM categories WHERE category_id IN (:ids) ORDER BY position ASC;")
    List<Category> findByIdsOrderByPosition(Set<Integer> ids);

    @Query(nativeQuery = true, value = "SELECT * FROM categories WHERE section_id = :sectionId ORDER BY position ASC;")
    List<Category> findAllBySectionIdOrderByPosition(Integer sectionId);
}
