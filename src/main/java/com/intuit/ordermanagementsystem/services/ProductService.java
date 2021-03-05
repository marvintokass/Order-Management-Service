package com.intuit.ordermanagementsystem.services;

import com.intuit.ordermanagementsystem.models.request.ProductCreateParams;
import com.intuit.ordermanagementsystem.models.dto.ProductDTO;
import com.intuit.ordermanagementsystem.models.dto.ProductPriceQuoteDTO;
import java.util.UUID;

public interface ProductService {

    ProductDTO createProduct(ProductCreateParams params);
    ProductDTO getProduct(UUID uuid);
    ProductPriceQuoteDTO getProductPriceQuote(UUID uuid);

}
