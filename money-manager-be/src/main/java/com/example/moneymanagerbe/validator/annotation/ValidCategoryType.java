package com.example.moneymanagerbe.validator.annotation;

import com.example.moneymanagerbe.validator.CategoryTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Constraint(validatedBy = {CategoryTypeValidator.class})
public @interface ValidCategoryType {

    String message() default "invalid.category.type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
