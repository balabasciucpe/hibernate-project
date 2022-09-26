package com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

//see: -> https://www.youtube.com/watch?v=IlLC3Yetil0
//see: -> https://stackoverflow.com/questions/72155637/a-way-of-polymorphic-http-requests-using-postman/72158992#72158992

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "promotion")
@JsonSubTypes(
        {       @JsonSubTypes.Type(value = PromotionEasterSeason.class, name = "easterPromotion"),
                @JsonSubTypes.Type(value = PromotionChristmasSeason.class, name = "christmasPromotion"),
                @JsonSubTypes.Type(value = NoPromotionForYouThisTimeMUHAHA.class, name = "noPromotion")
        })
public interface PromotionSeason {

    String isSeason();

     double applySeasonPromotionDiscount(double initialPrice, double procentageDiscount);


}
