package com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Service;

import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Product;
import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Exceptions.ProductNotFoundException;
import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@Service
@Validated
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public void createProduct(@Valid Product product) {
        productRepository.save(product);
    }

    public Product findById(Long productId)
    {
        return productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

    }

    public Product getByName(String productName) {
        Optional<Product> productOptional = Optional.ofNullable(productRepository.findProductByProductDescription_DescriptionName(productName));
        if(productOptional.isPresent())
        {
            return productOptional.get();
        }
        else
        {
            throw new ProductNotFoundException("this message is getting overwritted by that from ExceptionHandler");
        }
    }
}
