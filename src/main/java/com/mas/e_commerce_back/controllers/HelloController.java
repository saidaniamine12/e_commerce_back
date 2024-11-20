package com.mas.e_commerce_back.controllers;

import com.mas.e_commerce_back.entities.Section;
import com.mas.e_commerce_back.services.SectionService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.stereotype.Service;

@Service
@GraphQLApi
public class HelloController {

    private final SectionService sectionService;

    public HelloController(SectionService sectionService) {
        this.sectionService = sectionService;
    }


    @GraphQLQuery
        public Section hello(@GraphQLNonNull @GraphQLArgument(name = "id") Integer id) throws Exception {
        System.out.println("Hello world"+ id);
        return sectionService.getSectionById(1);
    }
}
