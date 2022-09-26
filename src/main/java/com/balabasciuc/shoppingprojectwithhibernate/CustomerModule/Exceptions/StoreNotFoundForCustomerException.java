package com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Exceptions;

public class StoreNotFoundForCustomerException extends CustomerException
{
    public StoreNotFoundForCustomerException(String message) {
        super(message);
    }
}
