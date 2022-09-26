package com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Controller;

import com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain.Promotion;
import com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/promotions")
public class PromotionController {

    private final PromotionService promotionService;

    @Autowired
    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @PostMapping(value = "/createPromotion")
    public ResponseEntity<String> createPromotion(@RequestBody Promotion promotion)
    {
        promotionService.createPromotion(promotion);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Done");
    }

    @GetMapping(value = "/createPromotion/{promotionType}")
    public ResponseEntity<Promotion> createPromotionType(@PathVariable String promotionType)
    {

        return ResponseEntity.status(HttpStatus.CREATED).body(promotionService.createPromotionWithType(promotionType));
    }

    @GetMapping(value = "/getPromotion/{promotionType}")
    public ResponseEntity<Promotion> getPromotionType(@PathVariable String promotionType)
    {
        return ResponseEntity.status(HttpStatus.FOUND).body(promotionService.getPromotionSeasonBasedOnSomething(promotionType));
    }

    @PostMapping("/addProducts/{productName}/{procentage}")
    public ResponseEntity<String> addProductsToPromotion(@RequestBody Promotion promotion, @PathVariable ("productName") String productName, @PathVariable double procentage) {
        {
            promotionService.addProducts(promotion, productName, procentage);
            return ResponseEntity.status(HttpStatus.CREATED).body("Done");
        }
    }

    @GetMapping(value = "/test/{promotion}")
    public Promotion check(@PathVariable String promotion)
    {
        return promotionService.test(promotion);
    }
}
