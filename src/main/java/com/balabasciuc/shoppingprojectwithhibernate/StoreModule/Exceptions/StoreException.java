package com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Exceptions;

public class StoreException extends RuntimeException {

    public StoreException() {
    }

    public StoreException(String message) {
        super(message);
    }
}
