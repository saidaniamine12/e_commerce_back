package com.mas.e_commerce_back.entities;

import io.leangen.graphql.annotations.types.GraphQLType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;


@Builder
@Entity
@Data
@Table(name = "sections")
@AllArgsConstructor
@NoArgsConstructor
@GraphQLType
public class Section {

    @Id
    @Column(name = "section_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sectionId;

    @NotBlank( message = "Invalid Name: Empty or Null value provided")
    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @Column(nullable = false)
    private Integer position;

    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY)
    List<Category> categories;

}
