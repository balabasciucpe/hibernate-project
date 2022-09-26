package com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.text.DecimalFormat;

@JsonTypeName(value = "christmasPromotion")
public class PromotionChristmasSeason implements PromotionSeason {

 //   @DefaultValue("10.2")
   // private double nowYouHaveToPayLessForChristmasWith;
      private double newPriceToPayForChristmasIs;

    @Override
    public String isSeason() {
        return "HO HO HO, SANTA GIVES YOU SOME BIG DISCOUNT THIS YEAR!";
    }

    @Override
    public double applySeasonPromotionDiscount(double initialPrice, double procentage) {
        isSeason();
        System.out.println("So you have to pay ONLY ONLY ONLY " + calculateDiscount(initialPrice, procentage));
        setNewPriceToPayForChristmasIs(calculateDiscount(initialPrice, procentage));
        return calculateDiscount(initialPrice, procentage);
    }

    private double calculateDiscount(double amountToPay, double procentage)
    {

        double newPriceIs = (amountToPay * procentage) / 100;
        System.out.println(amountToPay - newPriceIs);
     //   setNewPriceToPayForChristmasIs(getNewPriceToPayForChristmasIs() + getDigitsFormat(amountToPay - newPriceIs));
        return getDigitsFormat(amountToPay - newPriceIs);
    }

    public double getNewPriceToPayForChristmasIs() {
        return newPriceToPayForChristmasIs;
    }

    public void setNewPriceToPayForChristmasIs(double newPriceToPayForChristmasIs) {
        this.newPriceToPayForChristmasIs = newPriceToPayForChristmasIs;
    }

    private double getDigitsFormat(double numberToFormat)
    {
        DecimalFormat formatDecimal = new DecimalFormat("#.##");
        return Double.parseDouble(formatDecimal.format(numberToFormat));
    }
}
