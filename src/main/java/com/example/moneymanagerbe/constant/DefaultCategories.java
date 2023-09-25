package com.example.moneymanagerbe.constant;

import com.example.moneymanagerbe.domain.entity.Category;

import java.util.ArrayList;
import java.util.List;

public class DefaultCategories {
    public static List<Category> CategoriesConstant = new ArrayList<>();
    static {
        CategoriesConstant.add(new Category("Food & Beverage", TypeOfCategoryConstant.EXPENSE, "https://res.cloudinary.com/dhp1xcch9/image/upload/v1695647306/p-money-manager/default-category/beverage_945345_tqje97.png"));
        CategoriesConstant.add(new Category("Transportation", TypeOfCategoryConstant.EXPENSE, "https://res.cloudinary.com/dhp1xcch9/image/upload/v1695647304/p-money-manager/default-category/moon-rover_1300835_roefz6.png"));
        CategoriesConstant.add(new Category("Internet Bill", TypeOfCategoryConstant.EXPENSE, "https://res.cloudinary.com/dhp1xcch9/image/upload/v1695647304/p-money-manager/default-category/money_431130_wgyigv.png"));
        CategoriesConstant.add(new Category("Medical Check-up", TypeOfCategoryConstant.EXPENSE, "https://res.cloudinary.com/dhp1xcch9/image/upload/v1695647305/p-money-manager/default-category/Stethoscope_lexnwp.png"));
        CategoriesConstant.add(new Category("Pets", TypeOfCategoryConstant.EXPENSE, "https://res.cloudinary.com/dhp1xcch9/image/upload/v1695647302/p-money-manager/default-category/pets_bwloqk.png"));
        CategoriesConstant.add(new Category("Education", TypeOfCategoryConstant.EXPENSE, "https://res.cloudinary.com/dhp1xcch9/image/upload/v1695647659/p-money-manager/default-category/Education_tdpsxr.png"));
        CategoriesConstant.add(new Category("Home Services", TypeOfCategoryConstant.EXPENSE, "https://res.cloudinary.com/dhp1xcch9/image/upload/v1695647303/p-money-manager/default-category/Home_somkjt.png"));
        CategoriesConstant.add(new Category("Gift & Donations", TypeOfCategoryConstant.EXPENSE, "https://res.cloudinary.com/dhp1xcch9/image/upload/v1695647660/p-money-manager/default-category/Gift-Donations_p6wcsw.png"));
        CategoriesConstant.add(new Category("Payment", TypeOfCategoryConstant.EXPENSE, "https://res.cloudinary.com/dhp1xcch9/image/upload/v1695647304/p-money-manager/default-category/payment_zpyxgz.png"));
        CategoriesConstant.add(new Category("Salary", TypeOfCategoryConstant.INCOME, "https://res.cloudinary.com/dhp1xcch9/image/upload/v1695647302/p-money-manager/default-category/salary_eijgq7.png"));
    }
}
