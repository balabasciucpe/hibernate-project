package com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Utility;

import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Description;

import java.time.LocalDate;
import java.util.Date;

public class ProductProjection {

    public static class ProductDTO
    {
        private Description productDescription;
        private int productQuantity;
        private double productPrice;

        private LocalDate productExpDate;
        private Date productProducedAtDate;

        public ProductDTO(Description productDescription, int productQuantity, double productPrice, LocalDate productExpDate, Date productProducedAtDate) {
            this.productDescription = productDescription;
            this.productQuantity = productQuantity;
            this.productPrice = productPrice;
            this.productExpDate = productExpDate;
            this.productProducedAtDate = productProducedAtDate;
        }

        public Description getProductDescription() {
            return productDescription;
        }

        public int getProductQuantity() {
            return productQuantity;
        }

        public double getProductPrice() {
            return productPrice;
        }

        public LocalDate getProductExpDate() {
            return productExpDate;
        }

        public Date getProductProducedAtDate() {
            return productProducedAtDate;
        }
    }

    public static class ProductAtPromotion
    {
        private Description productDescription;
        private double productPrice;

        public ProductAtPromotion(Description productDescription, double productPrice) {
            this.productDescription = productDescription;
            this.productPrice = productPrice;
        }

        public Description getProductDescription() {
            return productDescription;
        }

        public double getProductPrice() {
            return productPrice;
        }
    }
}
