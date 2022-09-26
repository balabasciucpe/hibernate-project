package com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Domain;


import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Embeddable
public class Location {

    @Column(nullable = false) @NotBlank
    private String locationCity;
    @Column(nullable = false) @NotBlank
    private String locationCountry;

    @Embedded
    @NotNull
    @Column(nullable = false)
    @AttributeOverride(name = "zipCode", column = @Column(name = "LOCATION_ZIP_CODE"))
    @Valid
    private ZipCode locationZipCode;

    protected Location() {}

    public Location(String locationCity, String locationCountry, ZipCode locationZipCode) {
        this.locationCity = locationCity;
        this.locationCountry = locationCountry;
        this.locationZipCode = locationZipCode;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public String getLocationCountry() {
        return locationCountry;
    }

    public void setLocationCountry(String locationCountry) {
        this.locationCountry = locationCountry;
    }

    public ZipCode getLocationZipCode() {
        return locationZipCode;
    }

    public void setLocationZipCode(ZipCode locationZipCode) {
        this.locationZipCode = locationZipCode;
    }
}
