package com.mas.e_commerce_back.repositories;

import com.mas.e_commerce_back.entities.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {

    @Query(nativeQuery = true, value = "SELECT EXISTS (SELECT 1 FROM manufacturers WHERE name = :name);")
    boolean existsByName(@Param("name") String name);

    @Query(nativeQuery = true, value = "SELECT EXISTS (SELECT 1 FROM manufacturers WHERE manufacturer_id = :id);")
    boolean existsById(@Param("id") String id);

    @Query(nativeQuery = true, value = "SELECT * FROM manufacturers WHERE name = :name")
    Optional<Manufacturer> findByName(@Param("name") String name);

    @Query(nativeQuery = true, value = "SELECT * FROM manufacturers WHERE manufacturer_id = :id")
    Optional<Manufacturer> findById(@Param("id") String id);

    // find all
    @Query(nativeQuery = true, value = "SELECT * FROM manufacturers ORDER BY name ASC ;")
    List<Manufacturer> findAllOrderByName();

    // manufacturer has products
    @Query(nativeQuery = true, value = "SELECT EXISTS (SELECT 1 FROM products WHERE manufacturer_id = :id);")
    boolean productListExists(@Param("id") Integer id);

}
