package com.balabasciuc.shoppingprojectwithhibernate.StoreModuleTests;

import com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Domain.Category;
import com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Repository.CategoryRepository;

import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Domain.Address;
import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Domain.Customer;
import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Domain.CustomerDetails;
import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Repository.CustomerRepository;
import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Description;
import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Product;
import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Repository.ProductRepository;
import com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain.Promotion;
import com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain.PromotionChristmasSeason;
import com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain.PromotionEasterSeason;
import com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain.PromotionSeason;
import com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Repository.PromotionRepository;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Domain.Location;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Domain.Store;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Domain.ZipCode;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = com.balabasciuc.shoppingprojectwithhibernate.Configuration.ProjectHibernateBeanFactoryConfiguration.class)
public class ModuleTests {



    private StoreRepository storeRepository;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private PromotionRepository promotionRepository;
    private CategoryRepository categoryRepository;




    @Autowired
    public ModuleTests(StoreRepository storeRepository, CustomerRepository customerRepository, ProductRepository productRepository, PromotionRepository promotionRepository, CategoryRepository categoryRepository) {
        this.storeRepository = storeRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
        this.categoryRepository = categoryRepository;

    }

    @Test
    void testPromotion()
    {
        PromotionSeason easterPromotionSeason = new PromotionEasterSeason();
        Promotion promotion = new Promotion(easterPromotionSeason);
        PromotionSeason season2 = promotion.getPromotionSeason();

        promotionRepository.save(promotion);

      //  Promotion promotion2 = promotionRepository.findWhatPromotionSeasonWeHave("easterPromotion");

     //   Promotion promotion3 = promotionRepository.findPromotionByPromotionSeasonIsContaining("easterPromotion");

    //    System.out.println(promotion2.toString());
   //     System.out.println(promotion3.getPromotionSeason().toString());
    //    assertEquals(promotion2.getPromotionSeason().toString(), "easter");

      //  assertEquals("easterPromotion", promotion3.getPromotionSeason().toString());

       /* Session session = (Session) configuration.sessionFactory();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Promotion> criteriaQuery = cb.createQuery(Promotion.class);
        Root<Promotion> root = criteriaQuery.from(Promotion.class);
        criteriaQuery.select(root);
*/
    //    Query<Promotion> query = session.createQuery(criteriaQuery);
    //    List<Promotion> promotionList = query.getResultList();


        Specification<Promotion> promotionSeasonSpecification =
                (root1, criteriaQuery1, cb1) ->
                        cb1.like(root1.get("promotionSeason").as(String.class), "%EASTER%");
        Promotion e3 = promotionRepository.findById(1L).get();



        PromotionSeason promotionSeason = new PromotionChristmasSeason();
        Promotion promotionNew = new Promotion(promotionSeason);
        promotionRepository.save(promotionNew);
        Promotion promotionNeww = promotionRepository.findPromotionByPromotionSeason(promotionSeason);

    //    assertEquals("hohoho", promotionNeww.getPromotionSeason().isSeason());

    }

    @Test
    void someTest()
    {
        //Category
        Description categoryDescription = new Description("CHIPS", "VARIOUS CHIPS PRODUCTS");
        Category category = new Category(categoryDescription);
        categoryRepository.save(category);

        //Product
        Description description = new Description("LAYS - Chips", "Best chips in the worlds");
        Product product = new Product(description, 202.2,30);



        //Promotion
        Promotion promotion = new Promotion(30, new PromotionEasterSeason());


       //  product.setPromotion(promotion);
    //   product.setProductCategory(category); vezi daca le link-eaza
        promotion.getProductList().add(product);


        //link category-promotion   product
        category.setPromotion(promotion);
        category.getProductCollection().add(product);

        //Customer
        CustomerDetails customerDetails = new CustomerDetails("B", "Petru");
        Address address = new Address(customerDetails, "Suceava", "Romania", "haha@test.com");
        Customer customer = new Customer(address);





        //Store
        ZipCode storeZipCode = new ZipCode("12345");
        Location storeLocation = new Location("Suceava", "Romania", storeZipCode);
        Store store = new Store(storeLocation, "MEDICAL_STORE", "Catena");
        storeRepository.save(store);

        assertAll(
                () -> assertEquals("Suceava", customer.getCustomerAddress().getAddressCity()),
                () -> assertEquals(1, category.getProductCollection().size()),
                () -> assertEquals(30, category.getPromotion().getNumberOfProductsAtPromotion()),
                () -> assertEquals(30, promotion.getProductList().get(0).getProductQuantity())
        );
    }
}
