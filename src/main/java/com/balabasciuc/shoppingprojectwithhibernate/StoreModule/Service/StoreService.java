package com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Service;

import com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Domain.Category;
import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Product;
import com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain.Promotion;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Domain.Store;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Domain.StoreType;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Exceptions.StoreNotFoundException;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Repository.StoreRepository;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Utility.CallingOthers;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.text.DecimalFormat;
import java.util.*;


@Service
@Validated
public class StoreService {

    @Autowired
    private final StoreRepository storeRepository;

    @Autowired private CallingOthers callingOthers;

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public void save(@Valid Store store)
    {
        storeRepository.save(store);
    }



    public Store getStore(@NotNull String storeType) {
        return storeRepository.getStoreByStoreType(StoreType.valueOf(storeType));

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Store addCategory(String storeName, String category) {
        Store store = getStoreByStoreName(storeName);
        ResponseEntity<Category> category1 = callingOthers.getCategory(category);



        store.add(category1.getBody());

        Session session = entityManager.unwrap(Session.class);
        session.merge(category1.getBody());


        storeRepository.save(store);

        return store;
     //   return getStore(storeType);

    }

    public Store getStoreByStoreName(@NotBlank String storeName)
    {
        Optional<Store> storeOptional = Optional.ofNullable(storeRepository.getStoreByStoreName(storeName));
        return storeOptional.orElseThrow(StoreNotFoundException::new);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Store addPromotion(@NotBlank String storeName, @NotBlank String promotionType, @PositiveOrZero double procentage) {
        Optional<Store> storeOptional = Optional.ofNullable(getStoreByStoreName(storeName));
        if(storeOptional.isPresent()) {
            Optional<Promotion> promotion = Optional.ofNullable(callingOthers.getPromotionBasedOnSeason(promotionType.toLowerCase()));
            if(promotion.isPresent()) {
                   Session session = entityManager.unwrap(Session.class);
                    session.merge(promotion.get());


                storeOptional.get().setPromotion(promotion.get());

                Set<Category> categorySet = storeOptional.get().getCategorySet();
                List<Product> productCollection = new ArrayList<>();
                categorySet.forEach(category -> productCollection.addAll(category.getProductCollection()));

                promotion.get().setNumberOfProductsAtPromotion(numberOfProductsAtPromotion(productCollection));

                promotion.get().setProductList(productCollection);
                promotion.get().getProductList().forEach(
                        product -> product.setProductPrice(discountedPrice(promotion.get().getPromotionSeason().applySeasonPromotionDiscount(product.getProductPrice(), procentage))));



                // storeRepository.save(storeOptional.get());
                return storeOptional.get();
            }
            else
            {
                throw new StoreNotFoundException();
            }
        }
        else {
            throw new StoreNotFoundException();
        }
    }

    private double discountedPrice(double discountedProcent)
    {
        return getDigitsFormat(discountedProcent);
    }

    private double getDigitsFormat(double numberToFormat)
    {
        DecimalFormat formatDecimal = new DecimalFormat("#.##");
        return Double.parseDouble(formatDecimal.format(numberToFormat));
    }

    private int numberOfProductsAtPromotion(Collection<Product> productCollection)
    {
        return productCollection.stream().mapToInt(Product::getProductQuantity).sum();

    }
}
