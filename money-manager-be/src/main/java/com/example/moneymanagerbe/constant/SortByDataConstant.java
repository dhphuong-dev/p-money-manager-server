package com.example.moneymanagerbe.constant;

public enum SortByDataConstant implements SortByInterface {

  USER {
    @Override
    public String getSortBy(String sortBy) {
      switch (sortBy) {
        case "fullName":
          return "full_name";
        case "lastModifiedDate":
          return "last_modified_date";
        default:
          return "created_date";
      }
    }
  },

  Transaction {
    @Override
    public String getSortBy(String sortBy) {
      switch (sortBy) {
        case "name":
          return "name";
        case "total":
          return "total";
        case "date":
          return "date";
        case "location":
          return "location";
        case "withPerson":
          return "withPerson";
        case "lastModifiedDate":
          return "lastModifiedDate";
        default:
          return "createdDate";
      }
    }
  },

}
