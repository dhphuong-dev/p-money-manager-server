package com.example.moneymanagerbe.validator.annotation;

import com.example.moneymanagerbe.validator.TotalValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Constraint(validatedBy = {TotalValidator.class})
public @interface ValidTotal {

    String message() default "invalid.total-equal-zero";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
