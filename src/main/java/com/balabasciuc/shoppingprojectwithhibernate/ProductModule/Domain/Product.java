package com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Date;

@Entity
@org.hibernate.annotations.DynamicUpdate
@org.hibernate.annotations.DynamicInsert
@Access(AccessType.FIELD)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class    Product {




    @Id
    @SequenceGenerator(name = "product_generator", sequenceName = "PRODUCT_GENERATOR_ID")
    @GeneratedValue(generator = "product_generator")
    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Positive
    @Column(name = "PRODUCT_PRICE") @NotNull
    private double productPrice;

    @Positive
    @Column(name = "PRODUCT_QUANTITY") @NotNull
    private int productQuantity;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "descriptionName", column = @Column(name = "PRODUCT_NAME", nullable = false)),
    @AttributeOverride(name = "descriptionAbout", column = @Column(name = "PRODUCT_DESCRIPTION", nullable = false))})
    @Valid
    private Description productDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRODUCT_STATUS") @NotNull
    private ProductStatus productStatus = ProductStatus.PRODUCT_AVAILABLE;

    @FutureOrPresent
    @Column(name = "PRODUCT_EXPIRES_AT_")
    private LocalDate productExpDate;
    //aici poti sa faci ceva mai tare, gen o metoda sau

    @CreationTimestamp // will create a current TIMESTAMP and save it in DB
    @Column(name = "PRODUCED_AT_")
    private Date productProducedAtDate;



 /*   @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "PRODUCT_CATEGORY_ID")
    @NotNull
    private Category productCategory;
*/

  /*  @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customerProducts;
*/
   /* @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "PROMOTION_ID")
    private Promotion promotion;
*/

    public Product() {}

    public Product(Description productDescription, double productPrice, int productQuantity) {
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }

    public Long getProductId() {
        return productId;
    }

    public Description getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(Description productDescription) {
        this.productDescription = productDescription;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public LocalDate getProductExpDate() {
        return productExpDate;
    }

    public void setProductExpDate(LocalDate productExpDate) {
        this.productExpDate = productExpDate;
    }

    public Date getProductProducedAtDate() {
        return productProducedAtDate;
    }

    public void setProductProducedAtDate(Date productProducedAtDate) {
        this.productProducedAtDate = productProducedAtDate;
    }



    //recursive relationship -> out of flow error
 /*   public Category getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Category productCategory) {
        this.productCategory = productCategory;
    }
*/
/*
    public Customer getCustomerProducts() {
        return customerProducts;
    }

    public void setCustomerProducts(Customer customerProducts) {
        this.customerProducts = customerProducts;
    }
*/
    /*
    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    } */

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    @PrePersist
    void expiredAt()
    {
        this.productExpDate = LocalDate.of(2023, 2, 28);
    }



}
