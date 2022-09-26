package com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.UtilityProjections;

import com.balabasciuc.shoppingprojectwithhibernate.CustomerModule.Domain.Address;
import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Domain.Product;
import com.balabasciuc.shoppingprojectwithhibernate.ProductModule.Utility.ProductProjection;
import com.balabasciuc.shoppingprojectwithhibernate.StoreModule.Domain.Store;

import java.util.ArrayList;
import java.util.Collection;

public class CustomerProjection {

    public static class CustomerDTO
    {
        private Address customerAddress;

        private long amountToSpend;

        public CustomerDTO(Address customerAddress, long amountToSpend) {
            this.customerAddress = customerAddress;
            this.amountToSpend = amountToSpend;
        }

        public Address getCustomerAddress() {
            return customerAddress;
        }

        public long getAmountToSpend() {
            return amountToSpend;
        }
    }

    public static class CustomerFat
    {
        private Address customerAddress;

        private final Collection<Product> productCollection = new ArrayList<>();

        public CustomerFat(Address customerAddress) {
            this.customerAddress = customerAddress;
        }

        public Address getCustomerAddress() {
            return customerAddress;
        }

        public Collection<Product> getProductCollection() {
            return productCollection;
        }
    }

    public static class CustomerSkinny
    {

        private long amountToSpend;
        private Collection<ProductProjection.ProductDTO> productCollection = new ArrayList<>();

        public CustomerSkinny(long amountToSpend, Collection<ProductProjection.ProductDTO> productCollection) {
            this.amountToSpend = amountToSpend;
            this.productCollection = productCollection;
        }

        public long getAmountToSpend() {
            return amountToSpend;
        }

        public Collection<ProductProjection.ProductDTO> getProductCollection() {
            return productCollection;
        }
    }
}
