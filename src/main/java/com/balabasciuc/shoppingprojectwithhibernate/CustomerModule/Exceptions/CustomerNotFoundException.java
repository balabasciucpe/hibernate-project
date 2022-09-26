package com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Exceptions;



public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException() {
    }
}
