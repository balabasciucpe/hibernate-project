package com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.UtilityProjections;

import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Product;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Domain.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomerCalling {

    @Autowired
    private RestTemplate restTemplate;

    public Store callStore(String storeName) {
        return restTemplate.getForObject("http://localhost:8080/store/getStoreByName/" + storeName, Store.class);
    }

    public void saveStore(Store store)
    {
        restTemplate.postForObject("http://localhost:8080/store/create", store, Store.class);
    }

    public Product callProductByName(String productName)
    {
        return restTemplate.getForObject("http://localhost:8080/products/getProductByName"+productName, Product.class);
    }
}
