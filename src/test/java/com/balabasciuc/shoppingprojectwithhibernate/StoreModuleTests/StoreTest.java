package com.balabasciuc.shoppingprojectwithhibernate.StoreModuleTests;

import com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Domain.Category;
import com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Repository.CategoryRepository;
import com.balabasciuc.shoppingprojectwithhibernate.Configuration.ProjectHibernateBeanFactoryConfiguration;
import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Domain.Address;
import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Domain.Customer;
import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Domain.CustomerDetails;
import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Description;
import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Product;
import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Repository.ProductRepository;
import com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain.Promotion;
import com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain.PromotionEasterSeason;
import com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Repository.PromotionRepository;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Domain.Location;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Domain.Store;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Domain.ZipCode;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ProjectHibernateBeanFactoryConfiguration.class})
public class StoreTest {

    private final StoreRepository storeRepository;

    private final PromotionRepository promotionRepository;

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;



    @Autowired
    public StoreTest(StoreRepository storeRepository, PromotionRepository promotionRepository, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.storeRepository = storeRepository;
        this.promotionRepository = promotionRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }



    @Test
    void testStore()
    {



        //2 tipuri de set, prin add sau prin addList



        ZipCode zipCode = new ZipCode("12345");
        Location location = new Location("Suceava", "Romania", zipCode);


        //promotion
        Promotion promotionModule = new Promotion(9, new PromotionEasterSeason());
        //bidirectional cu relationship Product dar Product seteaza promotion = GATA
        //productList.setPromotion(promotionModule); = GATA
        //promotionModule.setStore... = GATA
        //promotionModule.setPromotionStore();

        Store store = new Store(location, "SHOPPING_STORE", "Abibas");

   //     promotionModule.setPromotionStore(store);
        store.setPromotion(promotionModule);
    //    promotionRepository.save(promotionModule);


        //category
        Description productCategoryDescription = new Description("CHIPS", "GOODS");
        Category productCategory = new Category(productCategoryDescription);
        productCategory.setPromotion(promotionModule);
        //in product iarasi seteaza categoria = GATA







        //Customer
        CustomerDetails customerDetails = new CustomerDetails("A", "Petru");
        Address address = new Address(customerDetails, "Suceava", "Romania", "ceva@email.com");
        Customer customer = new Customer(address);




        //Product
        Description productDescription = new Description("Lays Barbeque Chips", "Some Good Aroma idk i don't eat chips");
        Product product = new Product(productDescription, 20.2, 100);
        Description productDescription2 = new Description("Milka Chocolate", "best chocolate");
        Product product2 = new Product(productDescription2, 30.4, 24);
        //aici Price trebe setat din price = GATA
     //   product2.setProductCategory(productCategory);
      //  product2.setPromotion(promotionModule);
      //  product2.setCustomerProducts(customer);



        List<Product> productList = new ArrayList<>();
        productList.add(product2);

        promotionModule.setProductList(productList);
       // product2.setPromotion(promotionModule);

        productRepository.save(product2);
        store.add(productCategory);
        categoryRepository.save(productCategory);


        promotionRepository.save(promotionModule);


        Description description = new Description("Chips", "with barbeque");
        Promotion promotion = new Promotion(9, new PromotionEasterSeason());
        Category category = new Category(description);
        category.setPromotion(promotion);
        //mai avem Product List de pus aici la Category





        //store va fi ultimul salvat

        //bidirectiona have links between entities



        assertAll(
                () -> assertEquals(24, product2.getProductQuantity())
            //    () -> assertEquals("Romania", promotionModule.getPromotionStore().getStoreLocation().getLocationCountry())
        );




    }

}
