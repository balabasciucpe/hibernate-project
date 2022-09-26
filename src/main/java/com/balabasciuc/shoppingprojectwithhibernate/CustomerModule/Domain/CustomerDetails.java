package com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.*;

@Embeddable
public class CustomerDetails {

    @Column(nullable = false)
    @NotEmpty
    @Size(min = 5, max = 255, message = "Please provide a valid name!")
    private String customerName;

    @NotEmpty
    @Column(nullable = false)
    private String customerPrename;

    protected CustomerDetails() {}

    public CustomerDetails(String customerName, String customerPrename) {
        this.customerName = customerName;
        this.customerPrename = customerPrename;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPrename() {
        return customerPrename;
    }

    public void setCustomerPrename(String customerPrename) {
        this.customerPrename = customerPrename;
    }
}
