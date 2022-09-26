package com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Domain;



import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class ZipCode {

    @Column(nullable = false) @NotBlank
    @Size(min = 5, max = 10, message = "ZipCode should have size between {min} and {max}")
    private String zipCode;

    public ZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    protected ZipCode() {}

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
