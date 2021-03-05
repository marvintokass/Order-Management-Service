package com.intuit.ordermanagementsystem.models.response;

import lombok.Data;

@Data
public class ProductPriceQuote {
    VendorProductRelationDTO priceQuote;
    ProductDTO product;

    public ProductPriceQuote(ProductDTO product, VendorProductRelationDTO relation) {
        this.product = product;
        this.priceQuote = relation;
    }
}

