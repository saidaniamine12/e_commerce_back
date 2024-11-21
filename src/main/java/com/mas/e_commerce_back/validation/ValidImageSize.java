package com.mas.e_commerce_back.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageSizeValidator.class)
public @interface ValidImageSize {
    // Customizable error message with a default value
    String message() default "Invalid file type. Only JPG and PNG are allowed.";

    // image size in MB
    long size() default 5;
}
