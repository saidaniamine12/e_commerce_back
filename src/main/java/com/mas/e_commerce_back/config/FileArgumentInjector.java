package com.mas.e_commerce_back.config;

import graphql.schema.DataFetchingEnvironment;
import io.leangen.graphql.generator.mapping.ArgumentInjector;
import io.leangen.graphql.generator.mapping.ArgumentInjectorParams;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.context.request.NativeWebRequest;

import java.lang.reflect.Parameter;
import java.lang.reflect.AnnotatedType;

@Component
public class FileArgumentInjector implements ArgumentInjector {

    @Override
    public Object getArgumentValue(ArgumentInjectorParams params) {
        // Access the DataFetchingEnvironment from ResolutionEnvironment
        DataFetchingEnvironment env = (DataFetchingEnvironment) params.getResolutionEnvironment();

        // Get the NativeWebRequest (which is part of the Spring MVC context)
        NativeWebRequest webRequest = (NativeWebRequest) env.getContext();

        // Check if the request is a multipart request
        if (webRequest instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) webRequest;

            // Extract the file (parameter name should match the GraphQL mutation field)
            MultipartFile file = multipartRequest.getFile("file"); // "file" is the GraphQL field name
            return file;
        }

        return null; // Return null if it's not a multipart request
    }


    @Override
    public boolean supports(AnnotatedType type, Parameter parameter) {
        // Check if the parameter is of type MultipartFile
        return MultipartFile.class.isAssignableFrom(parameter.getType());
    }
}
