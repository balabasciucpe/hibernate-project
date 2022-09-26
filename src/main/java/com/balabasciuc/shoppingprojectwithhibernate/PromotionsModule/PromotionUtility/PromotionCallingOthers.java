package com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.PromotionUtility;

import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component //to be able to create a bean from this class
public class PromotionCallingOthers {

    private final RestTemplate restTemplate;

    @Autowired
    public PromotionCallingOthers(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public ResponseEntity<Product> callProduct(String descriptionProductName)
    {
        return restTemplate.getForEntity("http://localhost:8080/products/getProductByName/"+descriptionProductName, Product.class);
    }
}
