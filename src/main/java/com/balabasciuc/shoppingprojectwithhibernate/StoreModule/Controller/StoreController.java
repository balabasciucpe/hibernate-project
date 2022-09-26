package com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Controller;

import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Domain.Store;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/store")
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping(value = "/createStore", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void createStore(@RequestBody Store store)
    {
        storeService.save(store);
    }

    @GetMapping(value = "/{storeType}")
    public Store getStore(@PathVariable("storeType") String storeType)
    {
        return storeService.getStore(storeType);
    }

    @GetMapping(value = "getStoreByName/{storeName}")
    public Store getStoreByName(@PathVariable String storeName)
    {
        return storeService.getStoreByStoreName(storeName);
    }
    @GetMapping(value = "/addCategory/{storeName}/{category}")
    public Store addCategoryToStore(@PathVariable("storeName") String storeName, @PathVariable ("category") String category)
    {
        return storeService.addCategory(storeName, category);

    }

   @GetMapping(value = "/addPromotionToStore/{storeName}/{promotionType}/{procentage}")
    public ResponseEntity<Store> addPromotionToStore(@PathVariable String storeName, @PathVariable String promotionType, @PathVariable double procentage)
   {
       return ResponseEntity.status(HttpStatus.CREATED).body(storeService.addPromotion(storeName, promotionType, procentage));
   }


}
