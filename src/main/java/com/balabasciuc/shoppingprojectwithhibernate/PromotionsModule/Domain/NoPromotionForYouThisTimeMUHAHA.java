package com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(value = "noPromotion")
public class NoPromotionForYouThisTimeMUHAHA implements PromotionSeason {

    @Override
    public String isSeason() {
        return "Is the Season with not a single promotion...";
    }

    @Override
    public double applySeasonPromotionDiscount(double initialPrice, double procentage) {
        this.isSeason();
        System.out.println("You still have to pay: " + initialPrice);
        return initialPrice;
    }


    public NoPromotionForYouThisTimeMUHAHA() {}
}
