//package com.mas.e_commerce_back.controllers;
//
//import graphql.kickstart.servlet.GraphQLConfiguration;
//import graphql.kickstart.servlet.GraphQLHttpServlet;
//import graphql.schema.GraphQLSchema;
//import graphql.schema.StaticDataFetcher;
//import graphql.schema.idl.RuntimeWiring;
//import graphql.schema.idl.SchemaGenerator;
//import graphql.schema.idl.SchemaParser;
//import graphql.schema.idl.TypeDefinitionRegistry;
//import jakarta.servlet.annotation.WebServlet;
//
//import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;
//
//@WebServlet(name = "BroServlet", urlPatterns = {"*/*"}, loadOnStartup = 1)
//public class HelloServlet extends GraphQLHttpServlet {
//
//    @Override
//    protected GraphQLConfiguration getConfiguration() {
//        System.out.println("configuring");
//        return GraphQLConfiguration.with(createSchema()).build();
//    }
//
//    private GraphQLSchema createSchema() {
//        String schema = "type Query{broHiMe: String}";
//
//        SchemaParser schemaParser = new SchemaParser();
//        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);
//        System.out.println("working");
//        RuntimeWiring runtimeWiring = newRuntimeWiring()
//                .type("Query", builder -> builder.dataFetcher("broHiMe", new StaticDataFetcher("broHiMe")))
//                .build();
//        System.out.println("really working");
//        SchemaGenerator schemaGenerator = new SchemaGenerator();
//        return schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
//    }
//
//}