package com.mas.e_commerce_back.exceptions.handlers;


import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.Throwable;

@ControllerAdvice
public class CustomGraphQLGlobalExceptionHandler implements DataFetcherExceptionHandler {

    private final GraphQLExceptionHandlers exceptionHandlers ;

    // Map to store the handler methods for each exception type
    // using concurrent hash map to avoid concurrent modification exceptions
    private final Map<Class<? extends Throwable>, Method> handlerMethods = new ConcurrentHashMap<>();

    @Autowired
    public CustomGraphQLGlobalExceptionHandler(GraphQLExceptionHandlers exceptionHandlers) {
        this.exceptionHandlers = exceptionHandlers;
        // Register handler methods at startup
        for (Method method : this.exceptionHandlers.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(ExceptionHandler.class)) {
                for (Class<?> exceptionClass : method.getAnnotation(ExceptionHandler.class).value()) {
                    handlerMethods.put((Class<? extends Throwable>) exceptionClass, method);
                }
            }
        }
        System.out.println(handlerMethods);
    }



    @Override
    public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(DataFetcherExceptionHandlerParameters handlerParameters) {
        // Find the registered handler for the exception type
        // this can be logged to help with debugging but not now
        System.out.println("handleException : class name" + handlerParameters.getException().getClass().getName() +"message " + handlerParameters.getException().getMessage());

        Method handlerMethod = handlerMethods.get(handlerParameters.getException().getClass());
        if (handlerMethod != null) {
            try {
                DataFetcherExceptionHandlerResult result = (DataFetcherExceptionHandlerResult)
                        handlerMethod.invoke(this.exceptionHandlers, handlerParameters);

                return CompletableFuture.completedFuture(result);
            } catch (Exception e) {
                e.printStackTrace(); // Log reflection exceptions
                return CompletableFuture.completedFuture(this.exceptionHandlers.generalException(handlerParameters));
            }
        }

        // Fallback for unhandled exceptions
        return CompletableFuture.completedFuture(this.exceptionHandlers.generalException(handlerParameters));
    }


}
