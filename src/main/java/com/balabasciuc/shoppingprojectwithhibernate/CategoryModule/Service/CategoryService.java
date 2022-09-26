package com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Service;

import com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Domain.Category;
import com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Exceptions.CategoryNotFoundException;
import com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Repository.CategoryRepository;
import com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Utility.Projections.CallingFromCategory;
import com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Utility.Projections.CategoryProjections;
import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Description;
import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Product;
import com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain.Promotion;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CallingFromCategory callingFromCategoryTo;

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CallingFromCategory callingFromCategoryTo) {
        this.categoryRepository = categoryRepository;
        this.callingFromCategoryTo = callingFromCategoryTo;
    }

    public void createCategory(@Valid Category category) {
        categoryRepository.save(category);
    }

    public Category findCategoryById(@Min(1) Long categoryId) {

        return categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("We don't have that Category"));
    }

    public Category getCategoryByName(@NotBlank String categoryName) {
        Optional<Category> category = Optional.ofNullable(categoryRepository.getCategoryByCategoryDescription_DescriptionName(categoryName));
        if(category.isPresent())
        {
            return category.get();
        }
        else
        {
            throw new CategoryNotFoundException("sorry bout that");
        }
    }

    @Transactional(readOnly = true) // for the sake of example
    public List<CategoryProjections.DescriptionOnly> findByCategoryDescription(@Min(1) Long categoryId) {
        Optional<List<CategoryProjections.DescriptionOnly>> categoryOptional = Optional.ofNullable(categoryRepository.findByCategoryId(categoryId));
        if(categoryOptional.isPresent())
        {
            return categoryOptional.get();
        }
        else
        {
            throw new CategoryNotFoundException("we don't have that category");
        }
    }


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public Category addProductForThisCategory(@NotBlank String categoryName, @NotBlank String productName) {

        Category category = categoryRepository.getCategoryByCategoryDescription_DescriptionName(categoryName);
        ResponseEntity<Product> product = callingFromCategoryTo.callProduct(productName);

        category.addProduct(product.getBody());
     //   categoryRepository.save(category);
        return category;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED) // default
    public Category addPromotionToCategory(@NotBlank String categoryName,@NotBlank String promotionType, @PositiveOrZero double procentageDiscount) {

        //categoryRepository.save(category); already in a transaction because @Transactional
        return discountPromotionForCategory(categoryName, promotionType, procentageDiscount);
    }


    @Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED) // make sure the caller have a transaction, and use it
    public Category discountPromotionForCategory(String categoryName, String promotionType, double procentageDiscount)
    {
        Optional<Category> category = Optional.ofNullable(categoryRepository.getCategoryByCategoryDescription_DescriptionName(categoryName));
        if(category.isEmpty())
        {
            throw new CategoryNotFoundException("we don't have that Category, check again!");
        }

        Promotion promotion = callPromotion(promotionType);

   //     List<Product> productCollection = new ArrayList<>(category.getProductCollection());
   //     promotion.setProductList(productCollection);
   //         promotion.setNumberOfProductsAtPromotion(numberOfProductsAtPromotion(category.getProductCollection()));

        category.get().getProductCollection().forEach(promotion::addProduct);


        promotion.setNumberOfProductsAtPromotion(numberOfProductsAtPromotion(category.get().getProductCollection()));



        //vezi ca daca aplici 2 sezoane de reduceri consecutive, se aduna
        //adica nu e: reducere -> revine la cost initial -> reducere
        //ci din cost initial -> reducere -> reducere
        //poti face asta de mai jos intr-o metoda

        //merge acum, nu conteaza ca adaugam alt discount la cel existent, ramane acelasi practic
        //thanks to merge
        //dar daca adaugam altul peste cel existent,  gen christmas on easter merge
        category.get().getProductCollection()
                .forEach(product -> product.setProductPrice(
                        discountedPrice(promotion.getPromotionSeason().applySeasonPromotionDiscount(product.getProductPrice(), procentageDiscount))));

        Session session = entityManager.unwrap(Session.class);
        session.merge(promotion);


        promotion.getProductList().forEach(session::merge);
        
        category.get().setPromotion(promotion);
        //categoryRepository.save(category.get());
        return category.get();
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


    private Promotion callPromotion(String promotionType)
    {
       return callingFromCategoryTo.callPromotion(promotionType).getBody();
    }


    public Category modify(@Min(1) Long id, @Min(1) @Max(35) String name) {
        //just to test versioning
        Category category = categoryRepository.findById(id).get();
        String nombre1 = category.getCategoryDescription().getDescriptionAbout();
        category.setCategoryDescription(new Description(name, nombre1));
        categoryRepository.save(category);
        return category;
    }

    //testam Transactional, daca primim rollback la save
    @Transactional
    public Category test(Category category)
    {
        categoryRepository.save(category);
        throw new RuntimeException(":D");
    }



}
