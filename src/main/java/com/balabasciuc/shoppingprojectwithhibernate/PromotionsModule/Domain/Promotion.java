package com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain;

import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Product;
import com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.PromotionUtility.PromotionConverter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

@Entity
@org.hibernate.annotations.DynamicInsert
@org.hibernate.annotations.DynamicUpdate
@Access(AccessType.FIELD)
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Promotion {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) //pre Insert values
    private Long promotionId;

    @Column(name = "NUMBER_PRODUCTS_AT_PROMOTION", nullable = false)
    @PositiveOrZero
    private int numberOfProductsAtPromotion;

    //Strategy Pattern, maybe State pattern was more suitable?
    //check this -> https://stackoverflow.com/questions/51138344/hibernate-persisting-a-composition-interface-of-strategy-pattern
    @Convert(converter = PromotionConverter.class)
    @Column(name = "PROMOTION_SEASON", nullable = false)
    private PromotionSeason promotionSeason;

   // @OneToOne(fetch = FetchType.LAZY)
 //   @JoinColumn(name = "PROMOTION_STORE_ID")
 //   private Store promotionStore;

    //changes of mind
    //unidirectional
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.PERSIST)
    @NotNull
  //  @org.hibernate.annotations.OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "PROMOTION_PRODUCT_ID")
    private List<Product> productList = new ArrayList<>();

    public Promotion() {}

    public Promotion(PromotionSeason promotionSeason)
    {
        this.promotionSeason = promotionSeason;
    }

    public Promotion(int numberOfProductsAtPromotion, PromotionSeason promotionSeason) {
        this.numberOfProductsAtPromotion = numberOfProductsAtPromotion;
        this.promotionSeason = promotionSeason;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public int getNumberOfProductsAtPromotion() {
        return numberOfProductsAtPromotion;
    }

    public void setNumberOfProductsAtPromotion(int numberOfProductsAtPromotion) {
        this.numberOfProductsAtPromotion = numberOfProductsAtPromotion;
    }

    public PromotionSeason getPromotionSeason() {
        return promotionSeason;
    }

    public void setPromotionSeason(PromotionSeason promotionSeason) {
        this.promotionSeason = promotionSeason;
    }

 //   public Store getPromotionStore() {
   //     return promotionStore;
  //  }

  //  public void setPromotionStore(Store promotionStore) {
   //     this.promotionStore = promotionStore;
 //   }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public void addProduct(Product product)
    {
        this.productList.add(product);
     //   product.setPromotion(this);
    }

}
