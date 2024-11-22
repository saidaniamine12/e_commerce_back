package com.mas.e_commerce_back.config;

import com.mas.e_commerce_back.exceptions.handlers.CustomGraphQLGlobalExceptionHandler;
import com.mas.e_commerce_back.resolvers.ProductFilterParamsResolver;

import graphql.GraphQL;
import graphql.Scalars;
import graphql.execution.AsyncExecutionStrategy;
import graphql.execution.AsyncSerialExecutionStrategy;
import graphql.schema.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.util.List;

import static graphql.Scalars.GraphQLString;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final CustomGraphQLGlobalExceptionHandler customExceptionHandler;

    @Autowired
    public WebConfig(CustomGraphQLGlobalExceptionHandler customExceptionHandler) {
        this.customExceptionHandler = customExceptionHandler;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ProductFilterParamsResolver());
    }



    // Define GraphQL Bean with Custom Execution Strategies
    @Bean
    public GraphQL graphQL(GraphQLSchema schema) {
        // Custom Execution Strategies with error handling
        GraphQL.Builder builder = GraphQL.newGraphQL(schema)
                .queryExecutionStrategy(new AsyncExecutionStrategy(customExceptionHandler))
                .mutationExecutionStrategy(new AsyncSerialExecutionStrategy(customExceptionHandler));
        return builder.build();
    }

}

//    @Bean
//    public ExtensionProvider<GeneratorConfiguration, ArgumentInjector> multipartArgumentInjector() {
//        return (config, current) -> current.prepend(new ArgumentInjector() {
//
//            @Override
//            public Object getArgumentValue(ArgumentInjectorParams params) {
//
//                DataFetchingEnvironment env = (DataFetchingEnvironment) params.getResolutionEnvironment();
//
//                // Get the NativeWebRequest (which is part of the Spring MVC context)
//                NativeWebRequest webRequest = (NativeWebRequest) env.getContext();
//
//
//                // Check if the request is a multipart request
//                if (webRequest instanceof MultipartHttpServletRequest) {
//                    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) webRequest;
//
//                    // Extract the file (parameter name should match your GraphQL mutation field)
//                    MultipartFile file = multipartRequest.getFile("file"); // "file" is the GraphQL field name
//                    return file;
//                }
//
//                return null; // Return null if it's not a multipart request
//            }
//
//            @Override
//            public boolean supports(AnnotatedType type, Parameter parameter) {
//                // Check if the parameter is of type MultipartFile
//                return MultipartFile.class.isAssignableFrom(parameter.getType());
//            }
//        });
//
//    }

//    @Bean
//    public ExtensionProvider<GeneratorConfiguration, ArgumentInjector> multipartArgumentInjector() {
//        return (config, current) -> current.prepend(new FileArgumentInjector());
//    }


//    @Bean
//    public GraphQLScalarType uploadScalar() {
//        return GraphQLScalarType.newScalar()
//                .name("Upload")
//                .coercing(new UploadCoercing()) // Use a coercing that maps the file properly
//                .build();  // Register Upload scalar type
//    }

//    @Bean
//    public GraphQLSchema graphQLSchema(GraphQLSchemaGenerator graphQLSchemaGenerator) {
//        // Get dynamically generated GraphQL schema from SPQR
//        GraphQLSchema dynamicSchema = graphQLSchemaGenerator.generate();
//
//        // Define the custom query and mutation types (static ones)
//        GraphQLObjectType queryType = GraphQLObjectType.newObject()
//                .name("Query")
//                .field(GraphQLFieldDefinition.newFieldDefinition()
//                        .name("hello")
//                        .type(GraphQLString)
//                        .dataFetcher(environment -> "Hello, GraphQL!"))
//                .build();
//
//        GraphQLObjectType mutationType = GraphQLObjectType.newObject()
//                .name("Mutation")
//                .field(GraphQLFieldDefinition.newFieldDefinition()
//                        .name("uploadFile")
//                        .type(GraphQLString)
//                        .argument(GraphQLArgument.newArgument()
//                                .name("file")
//                                .type(UploadScalar.INSTANCE))
//                        .dataFetcher(environment -> {
//                            MultipartFile file = environment.getArgument("file");
//                            return "File uploaded successfully!";
//                        })
//                )
//                .build();
//
//        // Now we merge dynamic and static schemas
//        return GraphQLSchema.newSchema(dynamicSchema)
//                .query(queryType) // Add custom Query
//                .mutation(mutationType) // Add custom Mutation
//                .additionalType(UploadScalar.INSTANCE) // Register Upload Scalar
//                .build();
//    }
//
//    @Bean
//    public GraphQL graphQL(GraphQLSchema schema) {
//        // Custom Execution Strategies with error handling
//        GraphQL.Builder builder = GraphQL.newGraphQL(schema)
//                .queryExecutionStrategy(new AsyncExecutionStrategy(customExceptionHandler))
//                .mutationExecutionStrategy(new AsyncSerialExecutionStrategy(customExceptionHandler));
//        return builder.build();
//    }