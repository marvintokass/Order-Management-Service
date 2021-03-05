package com.intuit.ordermanagementsystem.models.response;

import com.intuit.ordermanagementsystem.models.Product;
import com.intuit.ordermanagementsystem.models.VendorProductRelation;
import lombok.Data;

@Data
public class ProductPriceQuote {
    Product product;
    VendorProductRelation bestQuote;

    public ProductPriceQuote(Product product, VendorProductRelation relation) {
        this.product = product;
        this.bestQuote = relation;
    }
}
