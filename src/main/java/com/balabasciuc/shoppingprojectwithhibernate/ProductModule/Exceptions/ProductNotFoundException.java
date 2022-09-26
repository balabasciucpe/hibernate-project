package com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Exceptions;

public class ProductNotFoundException extends ProductException {

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException() {}
}
