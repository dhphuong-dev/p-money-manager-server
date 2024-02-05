package com.example.moneymanagerbe.constant;

public class CloudinaryFolder {
    public final static String PREFIX = "p-money-manager";
    public final static String USERS = PREFIX + "/users";
    public final static String TRANSACTIONS = PREFIX + "/transactions";
    public final static String CATEGORIES = PREFIX + "/categories";

    public static String userAvatarFolder(String email) {
        return USERS + "/" + email + "/avatar";
    }

    public static String userTransactionsFolder(String email) {
        return USERS + "/" + email + "/transactions";
    }

    public static String userCategoriesFolder(String email) {
        return USERS + "/" + email + "/categories";
    }
}
