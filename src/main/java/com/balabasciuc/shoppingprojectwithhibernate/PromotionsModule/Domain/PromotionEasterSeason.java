package com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.text.DecimalFormat;

@JsonTypeName(value = "easterPromotion")
public class PromotionEasterSeason implements PromotionSeason{

    private double nowYouHaveToPayLessForEasterWith;

    @Override
    public String isSeason() {
        return "Is Easter Season Discount Time of the Year again!";
    }

    @Override
    public double applySeasonPromotionDiscount(double initialPrice, double procentage) {
        isSeason();
        System.out.println("Now you have to pay: " + calculateDiscount(initialPrice, procentage) + ", instead of: " + initialPrice);
        setNowYouHaveToPayLessForEasterWith(getNowYouHaveToPayLessForEasterWith() + calculateDiscount(initialPrice, procentage));
        return calculateDiscount(initialPrice, procentage);
    }

    private double calculateDiscount(double initialPriceToDiscount, double procentage)
    {
        double newPriceIs = (initialPriceToDiscount * procentage) / 100;
        return getDigitsFormat(initialPriceToDiscount - newPriceIs);
    }


    public double getNowYouHaveToPayLessForEasterWith() {
        return nowYouHaveToPayLessForEasterWith;
    }

    public void setNowYouHaveToPayLessForEasterWith(double nowYouHaveToPayLessForEasterWith) {
        this.nowYouHaveToPayLessForEasterWith = nowYouHaveToPayLessForEasterWith;
    }

    private double getDigitsFormat(double numberToFormat)
    {
        DecimalFormat formatDecimal = new DecimalFormat("#.##");
        return Double.parseDouble(formatDecimal.format(numberToFormat));
    }
}
