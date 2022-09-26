package com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Service;

import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Domain.Customer;
import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Domain.PurchasedDetails;
import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Exceptions.CustomerNotFoundException;
import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Exceptions.StoreNotFoundForCustomerException;
import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Repository.CustomerRepository;
import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.UtilityProjections.CustomerCalling;
import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.UtilityProjections.CustomerProjection;
import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Exceptions.ProductOutOfStockForCustomerException;
import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Product;
import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Utility.ProductProjection;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Domain.Store;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.*;

@Service
@Validated
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerCalling customerCalling;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerCalling customerCalling) {
        this.customerRepository = customerRepository;
        this.customerCalling = customerCalling;
    }

    @PersistenceContext
    private EntityManager entityManager;

    public void createCustomer(@Valid CustomerProjection.CustomerDTO customerDTO) {
        Customer customer = new Customer(customerDTO.getCustomerAddress(), customerDTO.getAmountToSpend());
        customerRepository.save(customer);
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public CustomerProjection.CustomerSkinny buyProducts(@NotBlank String storeName,  @NotBlank String productName, @NotBlank String customerName, @PositiveOrZero int numberOfProductsToBuy) {//fromStore

        Customer customer = getWholeCustomerByName(customerName);
        Optional<Store> storeOptional = Optional.ofNullable(customer.getStore());

        if (storeOptional.isPresent()) {
            //       Optional<Product> productOptional =
            //             Optional.ofNullable(storeOptional.get().getCategorySet().stream().filter(productOptionalName ->
            //                   productOptionalName.getProductCollection().stream().filter(product -> product.getProductDescription().getDescriptionName().equalsIgnoreCase(productName)).findFirst().orElseThrow(CustomerNotFoundException::new)));

            //          Optional<Product> productOptional = Optional.ofNullable(storeOptional.get().getCategorySet().stream().map(
            //                  category ->
            //                   {
            //                       category.getProductCollection().stream().filter(product -> product.getProductDescription().getDescriptionName().equalsIgnoreCase(productName)).findFirst().orElseThrow(CustomerNotFoundException::new);
            //                   }
            //           ));

            List<Product> productCollection = new ArrayList<>();

            storeOptional.get().getCategorySet().forEach(category -> productCollection.addAll(category.getProductCollection()));

            Optional<Product> productOptional = Optional.ofNullable(productCollection.stream().filter(product ->
                    product.getProductDescription().getDescriptionName().equalsIgnoreCase(productName)).findFirst().orElseThrow(CustomerNotFoundException::new));

            if (productOptional.isPresent()) {


                if (checkProduct(productOptional.get(), numberOfProductsToBuy, customer.getAmountToSpend())) {

                    System.out.println("aici suntem");
                    double productPrice = productOptional.get().getProductPrice() * numberOfProductsToBuy;
                    customer.setAmountToSpend((long) (customer.getAmountToSpend() - productPrice));


                    int productsPurchased = customer.getProductCollection().size();

                    Product product = productOptional.get();
                    product.setProductQuantity(product.getProductQuantity() - numberOfProductsToBuy);
                    customer.getProductCollection().add(product);

                    //aici e gresit, adica daca setez nr de produse pe care le ia consumeru, le va seta si product db, in loc de 30 va fi 3
                    //si calculeaza doar pretul per unit
                    //incearca sa faci un DTO
                    //facut mers
                    PurchasedDetails purchasedDetails = new PurchasedDetails(productName, numberOfProductsToBuy, productPrice);


                    customer.setPurchasedDetails(purchasedDetails);
                    return new CustomerProjection.CustomerSkinny(customer.getAmountToSpend(), Collections.singletonList(new ProductProjection.ProductDTO(product.getProductDescription(), numberOfProductsToBuy, productPrice, product.getProductExpDate(), product.getProductProducedAtDate())));
                }
            } else { throw new ProductOutOfStockForCustomerException("We don't have that product!"); }
        } else { throw new StoreNotFoundForCustomerException("This store doesn't exist, try another"); }

        return new CustomerProjection.CustomerSkinny(customer.getAmountToSpend(), null);
    }



    @Transactional
    public boolean checkProduct(@Valid Product product, @PositiveOrZero int quantity, @PositiveOrZero double price)
    {
        if((product.getProductQuantity() < quantity) || (product.getProductPrice() > price))
        {
            System.out.println("aici nu suntem");
            throw new ProductOutOfStockForCustomerException("Sorry, we need to refill your wallet or we need to refill our stock, come later");
        }
        System.out.println("aici suntem x2");
        return true;
    }


    public CustomerProjection.CustomerDTO getCustomerByName(@NotBlank String name) {

        Optional<CustomerProjection.CustomerDTO> optionalCustomerDTO = Optional.ofNullable(customerRepository.getCustomerByCustomerAddress_CustomerDetails_CustomerName(name));
        return optionalCustomerDTO.orElseThrow(() -> new CustomerNotFoundException("Doesn't exist"));
    }

    public CustomerProjection.CustomerFat getCustomerById(@Min(100) Long customerId)
    {
        Optional<CustomerProjection.CustomerFat> customerFatOptional = Optional.ofNullable(customerRepository.getFatCustomerByCustomerId(customerId));
        return customerFatOptional.orElseThrow(CustomerNotFoundException::new);
    }

    //just for example... see https://codete.com/blog/5-common-spring-transactional-pitfalls
   // @Transactional(readOnly = true)
    public Customer getWholeCustomerByName(@NotBlank String name) {

        Optional<Customer> customerOptional = Optional.ofNullable(customerRepository.findCustomerByName(name));
        return customerOptional.orElseThrow(CustomerNotFoundException::new);
    }

    public void save(@Valid Customer customer) {
        customerRepository.save(customer);
    }

    @Transactional
    public Customer customerStore(@NotBlank String storeName, @NotBlank String customerName) {

        Optional<Customer> customerOptional = Optional.ofNullable(customerRepository.findCustomerByCustomerAddress_CustomerDetails_CustomerName(customerName));
        Optional<Store> storeOptional = Optional.ofNullable(customerCalling.callStore(storeName));

        Session session = entityManager.unwrap(Session.class);

        if(customerOptional.isPresent() && storeOptional.isPresent())
        {


            session.update(storeOptional.get()); // see below
            storeOptional.get().getCategorySet().forEach(session::update); //detached entity passed to persist



            storeOptional.get().getCategorySet().forEach(
                            category ->
                            {
                                if((category.getPromotion()) != null && (!isEmpty(category.getPromotion().getProductList())))
                                {
                                    category.getPromotion().getProductList().forEach(session::update);
                                }
                            });


            storeOptional.get().getCategorySet().forEach(
                    category ->
                    {
                        if(category.getPromotion() != null)
                        {
                            session.update(category.getPromotion());

                        }
                    }
            );


            if(storeOptional.get().getPromotion() != null) {
                session.merge(storeOptional.get().getPromotion());
                if(!isEmpty(storeOptional.get().getPromotion().getProductList()))
                {
                    storeOptional.get().getPromotion().getProductList().forEach(session::merge);
                }
            }


            customerOptional.get().setStore(storeOptional.get());

            return customerOptional.get();
        } else
        {
            throw  new CustomerNotFoundException("Customer or Store not found, check again.");
        }
    }

    public boolean isEmpty(Collection<?> collection)
    {
        return (collection == null || collection.isEmpty());
    }


}
