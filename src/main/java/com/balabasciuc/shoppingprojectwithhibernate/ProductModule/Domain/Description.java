package com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class Description {

    @NotEmpty
    @Size(min = 5, max = 255, message = "Please provide a valid name!")
    private String descriptionName;
    @NotEmpty @Size(min = 5, max = 100, message = "Please write a description!")
    private String descriptionAbout;

    protected Description() {}

    public Description(String descriptionName, String descriptionAbout) {
        this.descriptionName = descriptionName;
        this.descriptionAbout = descriptionAbout;
    }

    public String getDescriptionName() {
        return descriptionName;
    }

    public String getDescriptionAbout() {
        return descriptionAbout;
    }

    public void setDescriptionName(String descriptionName) {
        this.descriptionName = descriptionName;
    }

    public void setDescriptionAbout(String descriptionAbout) {
        this.descriptionAbout = descriptionAbout;
    }
}
