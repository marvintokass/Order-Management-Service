package com.intuit.ordermanagementsystem.models.dto;

import lombok.Data;

@Data
public class ProductPriceQuoteDTO {
    VendorProductRelationDTO priceQuote;
    ProductDTO product;

    public ProductPriceQuoteDTO(ProductDTO product, VendorProductRelationDTO relation) {
        this.product = product;
        this.priceQuote = relation;
    }
}

