package com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Exceptions;

public class ProductOutOfStockForCustomerException extends CustomerException {

    public ProductOutOfStockForCustomerException(String message) {
        super(message);
    }
}
