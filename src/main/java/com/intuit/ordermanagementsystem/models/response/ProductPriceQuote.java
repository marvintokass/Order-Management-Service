package com.intuit.ordermanagementsystem.models.response;

import com.intuit.ordermanagementsystem.models.Product;
import com.intuit.ordermanagementsystem.models.VendorProductRelation;
import lombok.Data;

@Data
public class ProductPriceQuote {
    VendorProductRelation priceQuote;
    Product product;

    public ProductPriceQuote(Product product, VendorProductRelation relation) {
        this.product = product;
        this.priceQuote = relation;
    }
}
