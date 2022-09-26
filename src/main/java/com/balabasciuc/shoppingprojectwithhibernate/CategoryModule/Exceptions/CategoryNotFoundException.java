package com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Exceptions;

public class CategoryNotFoundException extends CategoryException {

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
