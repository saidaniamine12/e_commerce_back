package com.mas.e_commerce_back.repositories;

import com.mas.e_commerce_back.entities.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.Optional;


@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM Sections WHERE position = (SELECT MAX(position) FROM Sections);")
    Optional<Section> findLastSectionByPosition();

    @Query(nativeQuery = true, value = "SELECT * FROM Sections WHERE name = :name ;")
    Optional<Section> findByName(@Param("name") String name);

    @Query(nativeQuery = true, value = "SELECT * FROM Sections ORDER BY position ASC ;")
    List<Section> findAllOrderByPosition();

    @Query(nativeQuery = true, value = "SELECT * FROM Sections ORDER BY name ASC ;")
    List<Section> findAllByOrderByName();

    @Query(nativeQuery = true, value = "SELECT * FROM Sections WHERE section_id IN (:ids) ORDER BY position ASC;")
    List<Section> findByIdsOrderByPosition(@Param("ids") Set<Integer> ids);

    @Query(nativeQuery = true, value = "SELECT EXISTS (SELECT 1 FROM sections WHERE name = :sectionName);")
    boolean existsByName(@Param("sectionName") String sectionName);

    @Query(nativeQuery = true, value = "SELECT EXISTS (SELECT 1 FROM sections WHERE section_id = :id);")
    boolean existsById(@Param("id") Integer id);

    @Query(nativeQuery = true, value = "SELECT EXISTS (SELECT 1 FROM categories WHERE section_id = :sectionId);")
    boolean categoryListExists(@Param("sectionId") Integer sectionId);

}
