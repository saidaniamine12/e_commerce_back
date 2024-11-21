package com.mas.e_commerce_back.exceptions.handlers;

import com.mas.e_commerce_back.exceptions.*;

import graphql.GraphqlErrorBuilder;

import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GraphQLExceptionHandlers {


    private final static String INTERNAL_SERVER_ERROR_MESSAGE = "An internal server error occurred, please try again later.";
    private final static String VALIDATION_ERROR_MESSAGE = "Validation failed for one or more parameters.";
    private final static String BAD_REQUEST_MESSAGE = "The request is not invalid or malformed.";
    private final static String CONFLICT_MESSAGE = "the request conflicts with the current state of the target resource.";
    private final static String FORBIDDEN_MESSAGE = "require authentication to access the requested resources.";
    private final static String INSUFFICIENT_STORAGE_MESSAGE = "the server does not have the sufficient storage space to complete the request.";
    private final static String NOT_FOUND_MESSAGE = "the requested resource was not found.";
    private final static String UNAUTHORIZED_MESSAGE = "your do not have the necessary privileges to complete the request.";
    private final static String UNSUPPORTED_MEDIA_MESSAGE = "the content of the body is not supported by the server.";
    private final static String DATA_INTEGRITY_MESSAGE = "the request conflicts with the current state of the target resource.";
    private final static String INVALID_BODY_REQUEST_MESSAGE = "Invalid or missing request body. Please ensure the body is properly formatted and not empty.";

    private final static Map<String, Object> InternalServerErrorHashMap = new HashMap<String, Object>() {{
        put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }};
    private final static Map<String, Object> BadRequestHashMap = new HashMap<String, Object>() {{
        put("status", HttpStatus.BAD_REQUEST.value());
        put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
    }};
    private final static Map<String, Object> ConflictHashMap = new HashMap<String, Object>() {{
        put("status", HttpStatus.CONFLICT.value());
        put("error", HttpStatus.CONFLICT.getReasonPhrase());
    }};
    private final static Map<String, Object> ForbiddenHashMap = new HashMap<String, Object>() {{
        put("status", HttpStatus.FORBIDDEN.value());
        put("error", HttpStatus.FORBIDDEN.getReasonPhrase());
    }};
    private final static Map<String, Object> InsufficientStorageHashMap = new HashMap<String, Object>() {{
        put("status", HttpStatus.INSUFFICIENT_STORAGE.value());
        put("error", HttpStatus.INSUFFICIENT_STORAGE.getReasonPhrase());
    }};

    private final static Map<String, Object> NotFoundHashMap = new HashMap<String, Object>() {{
        put("status", HttpStatus.NOT_FOUND.value());
        put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
    }};
    private final static Map<String, Object> UnauthorizedHashMap = new HashMap<String, Object>() {{
        put("status", HttpStatus.UNAUTHORIZED.value());
        put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }};
    private final static Map<String, Object> UnsupportedMediaHashMap = new HashMap<String, Object>() {{
        put("status", HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        put("error", HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase());
    }};
    private final static Map<String, Object> DataIntegrityHashMap = new HashMap<String, Object>() {{
        put("status", HttpStatus.BAD_REQUEST.value());
        put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
    }};
    private final static Map<String, Object> InvalidBodyRequestHashMap = new HashMap<String, Object>() {{
        put("status", HttpStatus.BAD_REQUEST.value());
        put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
    }};



    // this is the default exception handler
    @ExceptionHandler(Exception.class)
    public DataFetcherExceptionHandlerResult generalException(DataFetcherExceptionHandlerParameters handlerParameters) {
        InternalServerErrorHashMap.put("timestamp", ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return DataFetcherExceptionHandlerResult.newResult()
                .error(GraphqlErrorBuilder.newError()
                        // to be changed in production - for now only in dev mode to see the error message
                        .message(handlerParameters.getException().getMessage())
                        .path(handlerParameters.getPath())
                        .locations(List.of(handlerParameters.getSourceLocation()))
                        .extensions(InternalServerErrorHashMap)
                        .build())
                .build();
    }


    // not found Exception
    @ExceptionHandler(NotFoundException.class)
    public DataFetcherExceptionHandlerResult handleNotFoundException(DataFetcherExceptionHandlerParameters handlerParameters) {
        NotFoundHashMap.put("timestamp", ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return DataFetcherExceptionHandlerResult.newResult()
                .error(GraphqlErrorBuilder.newError()
                        .message(handlerParameters.getException().getMessage())
                        .path(handlerParameters.getPath())
                        .locations(List.of(handlerParameters.getSourceLocation()))
                        .extensions(NotFoundHashMap)
                        .build())
                .build();
    }

    // for NotEmpty but it may ever be activated because
    // ConstraintViolationException is called for every validation
    // even in the method argument validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public DataFetcherExceptionHandlerResult handleValidationException(DataFetcherExceptionHandlerParameters handlerParameters) {
        BadRequestHashMap.put("timestamp", ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        MethodArgumentNotValidException ex = (MethodArgumentNotValidException) handlerParameters.getException();
        String message = ex.getAllErrors().stream()
                .map(violation -> violation.getDefaultMessage()).collect(Collectors.joining(", "));

        System.out.println("MethodArgumentNotValidException is called");
        return DataFetcherExceptionHandlerResult.newResult()
                .error(GraphqlErrorBuilder.newError()
                        .message(handlerParameters.getException().getMessage())
                        .path(handlerParameters.getPath())
                        .locations(List.of(handlerParameters.getSourceLocation()))
                        .extensions(BadRequestHashMap)
                        .build())
                .build();
    }

    // catching all errors related to the validation that is being used by jakarta.validation.constraints
    // like @NotNull, @NotBlank, @Size, @Email, @Min, @Max, @Pattern, @Positive, @PositiveOrZero, @Negative, @NegativeOrZero
    // this is called when the request body is not valid not the MethodArgumentNotValidException
    // because
    @ExceptionHandler(ConstraintViolationException.class)
    public DataFetcherExceptionHandlerResult handleConstraintViolationException(DataFetcherExceptionHandlerParameters handlerParameters) {

        ConstraintViolationException ex = (ConstraintViolationException) handlerParameters.getException();
        String message = ex.getConstraintViolations().stream()
                .map(violation -> violation.getMessage()).collect(Collectors.joining(", "));
        BadRequestHashMap.put("timestamp", ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return DataFetcherExceptionHandlerResult.newResult()
                .error(GraphqlErrorBuilder.newError()
                        .message(message)
                        .path(handlerParameters.getPath())
                        .locations(List.of(handlerParameters.getSourceLocation()))
                        .extensions(BadRequestHashMap)
                        .build())
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    public DataFetcherExceptionHandlerResult handleBadRequestException(DataFetcherExceptionHandlerParameters handlerParameters) {
        BadRequestHashMap.put("timestamp", ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return DataFetcherExceptionHandlerResult.newResult()
                .error(GraphqlErrorBuilder.newError()
                        .message(handlerParameters.getException().getMessage())
                        .path(handlerParameters.getPath())
                        .locations(List.of(handlerParameters.getSourceLocation()))
                        .extensions(BadRequestHashMap)
                        .build())
                .build();
    }

    @ExceptionHandler(ConflictException.class)
    public DataFetcherExceptionHandlerResult handleConflictException(DataFetcherExceptionHandlerParameters handlerParameters) {
        ConflictHashMap.put("timestamp", ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return DataFetcherExceptionHandlerResult.newResult()
                .error(GraphqlErrorBuilder.newError()
                        .message(handlerParameters.getException().getMessage())
                        .path(handlerParameters.getPath())
                        .locations(List.of(handlerParameters.getSourceLocation()))
                        .extensions(ConflictHashMap)
                        .build())
                .build();
    }

    @ExceptionHandler(ForbiddenException.class)
    public DataFetcherExceptionHandlerResult handleForbiddenException(DataFetcherExceptionHandlerParameters handlerParameters) {
        ForbiddenHashMap.put("timestamp", ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return DataFetcherExceptionHandlerResult.newResult()
                .error(GraphqlErrorBuilder.newError()
                        .message(handlerParameters.getException().getMessage())
                        .path(handlerParameters.getPath())
                        .locations(List.of(handlerParameters.getSourceLocation()))
                        .extensions(ForbiddenHashMap)
                        .build())
                .build();
    }


    @ExceptionHandler(InsufficientStorageException.class)
    public DataFetcherExceptionHandlerResult handleInsufficientStorageException(DataFetcherExceptionHandlerParameters handlerParameters) {
        InsufficientStorageHashMap.put("timestamp", ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return DataFetcherExceptionHandlerResult.newResult()
                .error(GraphqlErrorBuilder.newError()
                        .message(handlerParameters.getException().getMessage())
                        .path(handlerParameters.getPath())
                        .locations(List.of(handlerParameters.getSourceLocation()))
                        .extensions(InsufficientStorageHashMap)
                        .build())
                .build();
    }


     @ExceptionHandler(NoResourceFoundException.class)
    public DataFetcherExceptionHandlerResult handleNoResourceFoundException(DataFetcherExceptionHandlerParameters handlerParameters) {
        NotFoundHashMap.put("timestamp", ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return DataFetcherExceptionHandlerResult.newResult()
                .error(GraphqlErrorBuilder.newError()
                        .message("Resource not found.")
                        .path(handlerParameters.getPath())
                        .locations(List.of(handlerParameters.getSourceLocation()))
                        .extensions(NotFoundHashMap)
                        .build())
                .build();
    }

    @ExceptionHandler(UnauthorizedException.class)
    public DataFetcherExceptionHandlerResult handleUnauthorizedException(DataFetcherExceptionHandlerParameters handlerParameters) {
        UnauthorizedHashMap.put("timestamp", ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return DataFetcherExceptionHandlerResult.newResult()
                .error(GraphqlErrorBuilder.newError()
                        .message(handlerParameters.getException().getMessage())
                        .path(handlerParameters.getPath())
                        .locations(List.of(handlerParameters.getSourceLocation()))
                        .extensions(UnauthorizedHashMap)
                        .build())
                .build();
    }

    @ExceptionHandler(UnsupportedMediaTypeException.class)
    public DataFetcherExceptionHandlerResult handleUnsupportedMediaTypeException(DataFetcherExceptionHandlerParameters handlerParameters) {
        UnsupportedMediaHashMap.put("timestamp", ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return DataFetcherExceptionHandlerResult.newResult()
                .error(GraphqlErrorBuilder.newError()
                        .message(handlerParameters.getException().getMessage())
                        .path(handlerParameters.getPath())
                        .locations(List.of(handlerParameters.getSourceLocation()))
                        .extensions(UnsupportedMediaHashMap)
                        .build())
                .build();
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public DataFetcherExceptionHandlerResult handleDataIntegrityViolationException(DataFetcherExceptionHandlerParameters handlerParameters) {
        DataIntegrityHashMap.put("timestamp", ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return DataFetcherExceptionHandlerResult.newResult()
                .error(GraphqlErrorBuilder.newError()
                        .message(handlerParameters.getException().getMessage())
                        .path(handlerParameters.getPath())
                        .locations(List.of(handlerParameters.getSourceLocation()))
                        .extensions(DataIntegrityHashMap)
                        .build())
                .build();
    }

    // illegal argument exception
    @ExceptionHandler(IllegalArgumentException.class)
    public DataFetcherExceptionHandlerResult handleIllegalArgumentException(DataFetcherExceptionHandlerParameters handlerParameters) {
        BadRequestHashMap.put("timestamp", ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return DataFetcherExceptionHandlerResult.newResult()
                .error(GraphqlErrorBuilder.newError()
                        .message(handlerParameters.getException().getMessage())
                        .path(handlerParameters.getPath())
                        .locations(List.of(handlerParameters.getSourceLocation()))
                        .extensions(BadRequestHashMap)
                        .build())
                .build();
    }



}
