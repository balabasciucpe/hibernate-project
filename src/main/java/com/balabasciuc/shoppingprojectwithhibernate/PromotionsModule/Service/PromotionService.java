package com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Service;

import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Product;
import com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain.*;
import com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.PromotionUtility.PromotionCallingOthers;
import com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Repository.PromotionRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Optional;


@Service
@Validated
public class PromotionService {

    private final PromotionRepository promotionRepository;
    private final PromotionCallingOthers promotionCallingOthers;

    @PersistenceContext
    private EntityManager entityManager;



    @Autowired
    public PromotionService(PromotionRepository promotionRepository, PromotionCallingOthers promotionCallingOthers) {
        this.promotionRepository = promotionRepository;
        this.promotionCallingOthers = promotionCallingOthers;
    }

    public void createPromotion(@Valid Promotion promotion)
    {

        System.out.println(promotion.getPromotionSeason().isSeason());
        this.promotionRepository.save(promotion);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void addProducts(@Valid Promotion promotion, @NotBlank String productName, @PositiveOrZero double procentage) {
        Promotion createdPromotion = new Promotion(promotion.getPromotionSeason());

        ResponseEntity<Product> productResponseEntity = promotionCallingOthers.callProduct(productName);

        //thanks to Transactional -> we don't need this anymore
       //     Session session = entityManager.unwrap(Session.class);
       //     session.update(productResponseEntity.get().getBody()); // for detached entity error

            createdPromotion.addProduct(productResponseEntity.getBody());

            for(int i = 0; i < createdPromotion.getProductList().size(); i++) {
                double price = createdPromotion.getProductList().get(i).getProductPrice();
                double discountedPrice = createdPromotion.getPromotionSeason().applySeasonPromotionDiscount(price, procentage);


                double priceTo = getDigitsFormat(price - discountedPrice);
                Objects.requireNonNull(productResponseEntity.getBody()).setProductPrice(priceTo);

                createdPromotion.setNumberOfProductsAtPromotion(productResponseEntity.getBody().getProductQuantity());
            }
    }

    private double getDigitsFormat(double numberToFormat)
    {
        DecimalFormat formatDecimal = new DecimalFormat("#.##");
        return Double.parseDouble(formatDecimal.format(numberToFormat));
    }

    public Promotion createPromotionWithType(@NotBlank String promotionType) {
        Promotion promotion = new Promotion();
        promotion.setPromotionSeason(setPromotionSeasonImplBasedOnType(promotionType));
        promotionRepository.save(promotion);
        return promotion;
    }

    public Promotion getPromotionSeasonBasedOnSomething(@NotBlank String promotionType)
    {
        PromotionSeason promotionSeason = setPromotionSeasonImplBasedOnType(promotionType);
        System.out.println(promotionSeason.isSeason());
        Promotion promotion = promotionRepository.findPromotionByPromotionSeason(promotionSeason);
        System.out.println(promotion.getPromotionSeason());
        return promotion;
    }

    private PromotionSeason setPromotionSeasonImplBasedOnType(@NotBlank String promotionType)
    {
        // eh, state pattern would be better i guess
        switch (promotionType.toLowerCase()) {
            case "christmas":
                return new PromotionChristmasSeason();
            case "easter":
                return new PromotionEasterSeason();
            default:
                return new NoPromotionForYouThisTimeMUHAHA();
        }
    }




    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Promotion test(String testam) {
        PromotionSeason promotionSeason = checkPromotionSeason(testam);
        System.out.println("incepem metoda test: ");
        System.out.println(promotionSeason.isSeason());

        Promotion promotion = promotionRepository.findWhatPromotionSeasonWeHave(promotionSeason);


        if (promotion == null) {

            System.out.println("promotion season ii in if: " + promotionSeason.isSeason());
            Promotion promotion1 = new Promotion(promotionSeason);
            System.out.println(promotion1.getPromotionSeason().isSeason());
            //?
            promotion = promotion1;
            System.out.println(promotion.getPromotionSeason().isSeason());

       //     promotion.setPromotionStore(promotion1.getPromotionStore());
            System.out.println("heh aici suntem");
            promotionRepository.save(promotion);
            System.out.println(promotion.getPromotionSeason().isSeason());
            return promotion;
        }

        //urat workaround
        promotion.setPromotionSeason(promotionSeason); // atentie la null
        promotionRepository.save(promotion);
        System.out.println("promotion is" + promotion.getPromotionSeason().isSeason());
        return promotion;
    }

    private PromotionSeason checkPromotionSeason(String promotionSeason)
    {
        switch (promotionSeason.toLowerCase().trim())
        {
            case "easter" :
                return new PromotionEasterSeason();
            case "christmas" :
                return new PromotionChristmasSeason();

            default:
                return new NoPromotionForYouThisTimeMUHAHA();
        }
    }


}
