package com.example.moneymanagerbe.validator;

import com.example.moneymanagerbe.validator.annotation.ValidTotal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TotalValidator implements ConstraintValidator<ValidTotal, Float> {
    @Override
    public void initialize(ValidTotal constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Float target, ConstraintValidatorContext constraintValidatorContext) {
        return !target.equals(0.0f);
    }
}
