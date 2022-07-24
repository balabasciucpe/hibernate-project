package com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Embeddable
public class Address {

    @Embedded @NotNull
    private CustomerDetails customerDetails;

    @NotNull @Column(nullable = false)
    private String addressCity;

    @NotNull @Column(nullable = false)
    private String addressCountry;

    @NotNull @Column(nullable = false) @Email(message = "Please provide a valide email adress!")
    private String addressEmail;

    protected Address() {}

    public Address(CustomerDetails customerDetails, String addressCity, String addressCountry, String addressEmail) {
        this.customerDetails = customerDetails;
        this.addressCity = addressCity;
        this.addressCountry = addressCountry;
        this.addressEmail = addressEmail;
    }

    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public String getAddressEmail() {
        return addressEmail;
    }

    public void setAddressEmail(String addressEmail) {
        this.addressEmail = addressEmail;
    }
}
