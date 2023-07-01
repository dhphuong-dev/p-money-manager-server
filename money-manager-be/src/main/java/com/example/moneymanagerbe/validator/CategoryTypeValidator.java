package com.example.moneymanagerbe.validator;

import com.example.moneymanagerbe.constant.TypeOfCategoryConstant;
import com.example.moneymanagerbe.validator.annotation.ValidCategoryType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CategoryTypeValidator implements ConstraintValidator<ValidCategoryType, String> {
    @Override
    public boolean isValid(String type, ConstraintValidatorContext constraintValidatorContext) {
        if (type == null) return true;
        return TypeOfCategoryConstant.isValid(type);
    }
}
