package com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Domain;

import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Product;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Domain.Store;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@org.hibernate.annotations.DynamicUpdate
@org.hibernate.annotations.DynamicInsert
@Access(AccessType.FIELD)
public class Customer {

    @Id
    @GeneratedValue(generator = "ID_GENERATOR") //starts with index 100
    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Embedded @AttributeOverrides({
            @AttributeOverride(name = "addressCity", column = @Column(name = "CUSTOMER_CITY")),
    @AttributeOverride(name = "addressCountry", column=  @Column(name = "CUSTOMER_COUNTRY")),
            @AttributeOverride(name = "addressEmail", column = @Column(name = "CUSTOMER_EMAIL")),
    @AttributeOverride(name = "customerDetails.customerName", column = @Column(name = "CUSTOMER_NAME")),
    @AttributeOverride(name = "customerDetails.customerPrename", column = @Column(name = "CUSTOMER_PRENAME"))})
    @Valid
    private Address customerAddress;


    @Embedded @Column(nullable = false)
    @Valid
    private PurchasedDetails purchasedDetails;


    //uni
    //bidirectional because we want to see which products these customers have
    //or of what consumer these products are
    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
 //   @org.hibernate.annotations.OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "CUSTOMER_PRODUCT_ID")
    @NotNull // not nullable constraint in DDL
    private Collection<Product> productCollection = new ArrayList<>();


    //One Customer to a Store in the same time
    @OneToOne(orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @org.hibernate.annotations.OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "CUSTOMER_STORE_ID")
    private Store store;

    @PositiveOrZero
    @Column(name = "CUSTOMER_MONEY")
    private long amountToSpend;

    protected Customer() {}

    public Customer(Address customerAddress, long amountToSpend) {
        this.customerAddress = customerAddress;
        this.amountToSpend = amountToSpend;
    }


    public Customer(Address customerAddress) {
        this.customerAddress = customerAddress;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Address getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(Address customerAddress) {
        this.customerAddress = customerAddress;
    }


    public Collection<Product> getProductCollection() {
        return productCollection;
    }

    public void setProductCollection(Collection<Product> productCollection) {
        this.productCollection = productCollection;
    }

    public long getAmountToSpend() {
        return amountToSpend;
    }

    public void setAmountToSpend(long amountToSpend) {
        this.amountToSpend = amountToSpend;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public PurchasedDetails getPurchasedDetails() {
        return purchasedDetails;
    }

    public void setPurchasedDetails(PurchasedDetails purchasedDetails) {
        this.purchasedDetails = purchasedDetails;
    }
}
