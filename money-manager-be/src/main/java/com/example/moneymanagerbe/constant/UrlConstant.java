package com.example.moneymanagerbe.constant;

public class UrlConstant {

  public static class Auth {
    private static final String PRE_FIX = "/auth";

    public static final String LOGIN = PRE_FIX + "/login";
    public static final String REGISTER = PRE_FIX + "/register";
    public static final String LOGOUT = PRE_FIX + "/logout";

    private Auth() {
    }
  }

  public static class User {
    private static final String PRE_FIX = "/user";

    public static final String GET_USERS = PRE_FIX;
    public static final String GET_USER = PRE_FIX + "/{userId}";
    public static final String GET_CURRENT_USER = PRE_FIX + "/current";

    private User() {
    }
  }

  public static class Budget {
    private static final String PRE_FIX = "/budget";

    public static final String GET_BUDGETS = PRE_FIX;
    public static final String GET_BUDGET = PRE_FIX + "/{userId}";
    public static final String POST_NEW_BUDGET = PRE_FIX;
    public static final String UPDATE_NAME_BUDGET = PRE_FIX + "/update-name/{id}";
    public static final String UPDATE_TOTAL_BUDGET = PRE_FIX + "/update-total/{id}";
    public static final String DELETE_BUDGETS = PRE_FIX + "/{id}";

    private Budget() {
    }
  }

  public static class Category {
    private static final String PRE_FIX = "/category";

    public static final String GET_CATEGORIES = PRE_FIX;
    public static final String POST_NEW_CATEGORY = PRE_FIX;
    public static final String DELETE_CATEGORY = PRE_FIX + "/{id}";
  }

}
