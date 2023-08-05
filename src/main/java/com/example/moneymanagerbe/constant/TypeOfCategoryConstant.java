package com.example.moneymanagerbe.constant;

public class TypeOfCategoryConstant {
    public static final String INCOME = "INCOME";
    public static final String EXPENSE = "EXPENSE";

    public static Boolean isValid(String target) {
        return target.equals(INCOME) || target.equals(EXPENSE);
    }
}
