package com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Domain;

import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Description;
import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Product;
import com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain.Promotion;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@org.hibernate.annotations.DynamicInsert
@org.hibernate.annotations.DynamicUpdate
@Access(AccessType.FIELD)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Category {

    @Id
    @SequenceGenerator(name = "category_generator", sequenceName = "CATEGORY_GENERATOR_ID")
    @GeneratedValue(generator = "category_generator")
    @Column(name = "CATEGORY_ID")
    private Long categoryId;


    @Version
    private Long categoryVersion;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "descriptionName", column = @Column(name = "CATEGORY_NAME", nullable = false)),
            @AttributeOverride(name = "descriptionAbout", column = @Column(name = "CATEGORY_DESCRIPTION", nullable = false))})
    @Valid
    private Description categoryDescription;

    //search category for this product
    //search products for this category
    //-> bidirectional
    // @OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "PRODUCT_CATEGORY_ID")
    //maked unidirectional because recursivity relationship
  //  @org.hibernate.annotations.OnDelete(action = OnDeleteAction.CASCADE)
    //^ daca e referentiata aceasta instanta de mai multe entitati/instante, iar una dintre acestea sterge, se sterge din toate
    //nu e prea ok, vrem doar sa se stearga din tabela instantei care o sterge
    //thx hibernate
    private Collection<Product> productCollection = new ArrayList<>();


    //unidirectional
    //https://stackoverflow.com/questions/24994440/no-serializer-found-for-class-org-hibernate-proxy-pojo-javassist-javassist
    //(fetch = FetchType.LAZY)
    @OneToOne(fetch = FetchType.EAGER,  orphanRemoval = true)
    @JoinColumn(name = "PROMOTION_ID")
    private Promotion promotion;

    protected Category() {
    }

    public Category(Description categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Description getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(Description categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public Collection<Product> getProductCollection() {
        return productCollection;
    }

    public void setProductCollection(Collection<Product> productCollection) {
        this.productCollection = productCollection;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }


    public void addProduct(Product product) {
        this.productCollection.add(product);
    }


    public Long getCategoryVersion() {
        return categoryVersion;
    }
}
