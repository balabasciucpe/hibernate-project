package com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Controller;

import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Product;
import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping(value = "/products")
@Validated
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/createProduct")
    public ResponseEntity<String> create(@Valid @RequestBody Product product) {
        productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product " + product.getProductDescription().getDescriptionName() + " was created!");
    }

    @GetMapping(value = "/getProduct/{id}")
    //error if id < 5 for the sake of example
    public ResponseEntity<Product> getProduct(@PathVariable @Min(5) Long id)
    {
        return ResponseEntity.status(HttpStatus.FOUND).body(productService.findById(id));
    }

    @GetMapping(value = "/getProductByName/{productName}")
    public ResponseEntity<Product> getProductByName(@PathVariable @NotBlank String productName)
    {
        return ResponseEntity.status(HttpStatus.FOUND).body(productService.getByName(productName));
    }

}
