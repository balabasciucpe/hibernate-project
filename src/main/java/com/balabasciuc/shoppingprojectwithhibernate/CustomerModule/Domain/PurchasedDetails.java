package com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Embeddable
@Access(AccessType.FIELD)
public class PurchasedDetails {

    //all of them are reference class types because hibernate will throw an error for not null property violated

    @NotBlank
    private String purchasedItemName;

    @PositiveOrZero
    private Integer purchasedItemQuantity;
    @PositiveOrZero
    private Double purchasedItemPrice;

    public PurchasedDetails(String purchasedItemName, Integer purchasedItemQuantity, Double purchasedItemPrice) {
        this.purchasedItemName = purchasedItemName;
        this.purchasedItemQuantity = purchasedItemQuantity;
        this.purchasedItemPrice = purchasedItemPrice;
    }

    public PurchasedDetails() {}

    public String getPurchasedItemName() {
        return purchasedItemName;
    }

    public void setPurchasedItemName(String purchasedItemName) {
        this.purchasedItemName = purchasedItemName;
    }

    public Integer getPurchasedItemQuantity() {
        return purchasedItemQuantity;
    }

    public void setPurchasedItemQuantity(Integer purchasedItemQuantity) {
        this.purchasedItemQuantity = purchasedItemQuantity;
    }

    public Double getPurchasedItemPrice() {
        return purchasedItemPrice;
    }

    public void setPurchasedItemPrice(Double purchasedItemPrice) {
        this.purchasedItemPrice = purchasedItemPrice;
    }

    public Double getPriceForQuantity(Integer purchasedItemQuantity, Double purchasedItemPrice)
    {
        return purchasedItemQuantity * purchasedItemPrice;
    }
}
