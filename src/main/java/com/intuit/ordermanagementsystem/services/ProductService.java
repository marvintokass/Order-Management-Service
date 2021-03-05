package com.intuit.ordermanagementsystem.services;

import com.intuit.ordermanagementsystem.models.Product;
import com.intuit.ordermanagementsystem.models.request.ProductCreateParams;
import com.intuit.ordermanagementsystem.models.response.ProductPriceQuote;

import java.util.UUID;

public interface ProductService {
    Product createProduct(ProductCreateParams params);
    Product getProduct(UUID uuid);
    ProductPriceQuote getProductPriceQuote(UUID uuid);
}
