package com.balabasciuc.shoppingprojectwithhibernate.Exceptions;

import com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Exceptions.CategoryNotFoundException;
import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Exceptions.CustomerNotFoundException;
import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Exceptions.ProductOutOfStockForCustomerException;
import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Exceptions.StoreNotFoundForCustomerException;
import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Exceptions.ProductNotFoundException;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Exceptions.StoreNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ShoppingProjectExceptionController extends ResponseEntityExceptionHandler {

    //MethodArgumentNotValidException.class

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> customerEx(ConstraintViolationException ex)
    {
        return new ResponseEntity<>("Check input's again, something is wrong!", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> categoryNotFoundException(CategoryNotFoundException ex)
    {
        return new ResponseEntity<>("Sorry, came back another Time", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductOutOfStockForCustomerException.class)
    public ResponseEntity<String> productOutOfStock(ProductOutOfStockForCustomerException pr)
    {
        return new ResponseEntity<>("Sorry, we need to refill your wallet or we need to refill our stock, come later", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StoreNotFoundForCustomerException.class)
    public ResponseEntity<String> storeNotFoundExceptionForCustomer(StoreNotFoundForCustomerException ex)
    {
        return new ResponseEntity<>("test", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> productNotFound(ProductNotFoundException px)
    {
        return new ResponseEntity<>("We don't have this Product, check another!", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> customerNotFound(CustomerNotFoundException ex)
    {
        return new ResponseEntity<>("This Customer we don't know - Baby Yoda", HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(StoreNotFoundException.class)
    public ResponseEntity<String> storeNotFound(StoreNotFoundException ex)
    {
        return new ResponseEntity<>("Store not found!", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<String> huh(HttpClientErrorException.NotFound ex)
    {
        return new ResponseEntity<>("huh", HttpStatus.NOT_FOUND);
    }

}
