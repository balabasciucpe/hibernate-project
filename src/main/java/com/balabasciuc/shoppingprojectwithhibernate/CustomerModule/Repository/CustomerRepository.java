package com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Repository;

import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Domain.Customer;
import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.UtilityProjections.CustomerProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //i love Spring with these runtime generated queries
    CustomerProjection.CustomerDTO getCustomerByCustomerAddress_CustomerDetails_CustomerName(String customerName);

    CustomerProjection.CustomerFat getFatCustomerByCustomerId(Long customerId);

    //long, i know...
    @Query(value = "SELECT customer FROM Customer customer WHERE customer.customerAddress.customerDetails.customerName = :customerName")
    Customer findCustomerByName(@Param("customerName") String customerName);

    Customer findCustomerByCustomerAddress_CustomerDetails_CustomerName(String customerName);
}
