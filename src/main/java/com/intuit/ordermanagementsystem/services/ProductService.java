package com.intuit.ordermanagementsystem.services;

import com.intuit.ordermanagementsystem.models.request.ProductCreateParams;
import com.intuit.ordermanagementsystem.models.response.ProductDTO;
import com.intuit.ordermanagementsystem.models.response.ProductPriceQuote;

import java.util.UUID;

public interface ProductService {
    ProductDTO createProduct(ProductCreateParams params);
    ProductDTO getProduct(UUID uuid);
    ProductPriceQuote getProductPriceQuote(UUID uuid);
}
