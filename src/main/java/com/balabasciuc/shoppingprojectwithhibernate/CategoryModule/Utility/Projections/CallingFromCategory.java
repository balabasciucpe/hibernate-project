package com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Utility.Projections;

import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Product;
import com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;
import java.util.Optional;

@Component
public class CallingFromCategory {

    private final RestTemplate restTemplate;

    @Autowired
    public CallingFromCategory(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<Product> callProduct(String descriptionProductName)
    {
        return restTemplate.getForEntity("http://localhost:8080/products/getProductByName/"+descriptionProductName, Product.class);
    }

    public ResponseEntity<Promotion> callPromotion(String promotionType) {
        ResponseEntity<Promotion> promotion = checkPromotion(promotionType);

        if(promotion != null)
        {
            return promotion;
        }
        else
        {
        return restTemplate.getForEntity("http://localhost:8080/promotions/createPromotion/"+promotionType, Promotion.class);
    }
    }

    private ResponseEntity<Promotion> checkPromotion(String promotionType)
    {
        ResponseEntity<Promotion> promotion = restTemplate.getForEntity("http://localhost:8080/promotions/test/"+promotionType.toLowerCase(Locale.ROOT), Promotion.class);
        System.out.println(promotion.getBody().getPromotionSeason().isSeason());
        Optional<Promotion> promotionOptional = Optional.ofNullable(promotion.getBody());
        if(promotionOptional.isPresent())
        {
            return ResponseEntity.status(HttpStatus.FOUND).body(promotionOptional.get());
        }
        else return null;
    }
}
