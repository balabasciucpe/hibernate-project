package com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Exceptions;

public class StoreNotFoundException extends StoreException {
    public StoreNotFoundException() {
        super();
    }

    public StoreNotFoundException(String message) {
        super(message);
    }
}
