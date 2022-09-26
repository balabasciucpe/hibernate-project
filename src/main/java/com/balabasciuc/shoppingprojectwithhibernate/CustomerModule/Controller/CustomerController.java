package com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Controller;

import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Domain.Customer;
import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Service.CustomerService;
import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.UtilityProjections.CustomerProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(value = "/createCustomer")
    public ResponseEntity<String> createCustomer(@RequestBody CustomerProjection.CustomerDTO customerDTO)
    {
        customerService.createCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("CUSTOMER", "CREATED")
                .body("Customer was created!");
    }

    @PostMapping(value = "/create")
    public void create(@RequestBody Customer customer)
    {
        customerService.save(customer);
    }


    @GetMapping(value = "/getCustomerByName")
    public ResponseEntity<CustomerProjection.CustomerDTO> getCustomerByName(@RequestParam String name)
    {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Customer ", name.toString())
                .body(customerService.getCustomerByName(name));
    }

    @GetMapping(value = "/getWholeCustomerByName")
    public ResponseEntity<Customer> getWholeCustomerByName(@RequestParam String name)
    {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Customer ", name.toString())
                .body(customerService.getWholeCustomerByName(name));
    }


    @GetMapping(value = "/getCustomerById")
    public ResponseEntity<CustomerProjection.CustomerFat> getAllKindOfInfos(@RequestParam Long customerId)
    {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .header("Customer was found for id ", customerId.toString())
                .body(customerService.getCustomerById(customerId));
    }

    @GetMapping(value ="/goIn/{storeName}/{customerName}")
    public ResponseEntity<Customer> customerInStore(@PathVariable String storeName, @PathVariable String customerName)
    {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(customerService.customerStore(storeName, customerName));
    }

    @GetMapping(value = "/buyProducts/{storeName}/{productName}/{customerName}/{numberOfProducts}")
    public ResponseEntity<CustomerProjection.CustomerSkinny> buyProducts(@PathVariable String storeName, @PathVariable String productName, @PathVariable String customerName, @PathVariable int numberOfProducts)
    {
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT)
                .body(customerService.buyProducts(storeName, productName, customerName, numberOfProducts));
    }
}
