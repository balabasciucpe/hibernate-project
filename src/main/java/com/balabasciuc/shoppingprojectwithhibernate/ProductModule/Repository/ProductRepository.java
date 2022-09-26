package com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Repository;

import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductByProductDescription_DescriptionName(String productName);
}
