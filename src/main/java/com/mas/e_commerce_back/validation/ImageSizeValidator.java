package com.mas.e_commerce_back.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;


public class ImageSizeValidator implements ConstraintValidator<ValidImageSize, MultipartFile> {

    private String message;
    private long size;
    @Override
    public void initialize(ValidImageSize constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.message = constraintAnnotation.message();
        this.size = constraintAnnotation.size() * 1024 * 1024;
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if(value == null){
            return false;
        }
        if(value.getSize() > size){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        return true;
    }
}
