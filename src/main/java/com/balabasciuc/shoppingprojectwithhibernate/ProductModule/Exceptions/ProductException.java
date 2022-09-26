package com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Exceptions;

public class ProductException extends RuntimeException {

    public ProductException(String message) {
        super(message);
    }

    public ProductException() {
    }
}
