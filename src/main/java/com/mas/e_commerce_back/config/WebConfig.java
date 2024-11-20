package com.mas.e_commerce_back.config;

import com.mas.e_commerce_back.exceptions.handlers.CustomGraphQLGlobalExceptionHandler;
import com.mas.e_commerce_back.resolvers.ProductFilterParamsResolver;

import graphql.GraphQL;
import graphql.execution.AsyncExecutionStrategy;
import graphql.execution.AsyncSerialExecutionStrategy;
import graphql.schema.GraphQLSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    List<ResolverInterceptor> globalInterceptors = Collections.singletonList(new GlobalExceptionHandler());

    private final CustomGraphQLGlobalExceptionHandler customExceptionHandler;

    @Autowired
    public WebConfig(CustomGraphQLGlobalExceptionHandler customExceptionHandler) {
        this.customExceptionHandler = customExceptionHandler;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ProductFilterParamsResolver());
    }

//
//    @Bean
//    public ExtensionProvider<GeneratorConfiguration, ResolverInterceptorFactory> customInterceptors() {
//        return (config, interceptors) -> interceptors.append(new GlobalResolverInterceptorFactory(globalInterceptors));
//    }

    @Bean
    public GraphQL graphQL(GraphQLSchema schema) {
        GraphQL.Builder builder = GraphQL.newGraphQL(schema)
                .queryExecutionStrategy(new AsyncExecutionStrategy(customExceptionHandler))
                .mutationExecutionStrategy(new AsyncSerialExecutionStrategy(customExceptionHandler));
        return builder.build();
    }


}
