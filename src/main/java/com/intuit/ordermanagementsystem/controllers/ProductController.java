package com.intuit.ordermanagementsystem.controllers;

import com.intuit.ordermanagementsystem.models.request.ProductCreateParams;
import com.intuit.ordermanagementsystem.models.dto.ProductDTO;
import com.intuit.ordermanagementsystem.models.dto.ProductPriceQuoteDTO;
import com.intuit.ordermanagementsystem.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductService productService;
    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping(produces = "application/json")
    ResponseEntity<ProductDTO> createProduct(@RequestBody ProductCreateParams productCreateParams) {
        logger.info("\ncreating product with params :- \n" + productCreateParams.toString() + " \n");
        validateProductCreationParams(productCreateParams);
        ProductDTO product = productService.createProduct(productCreateParams);
        logger.info("\nproduct created :- \n" + product.toString() + " \n");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "products/" + product.getUuid());
        return new ResponseEntity<>(product, headers, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{uuid}", produces = "application/json")
    ResponseEntity<ProductDTO> getProduct(@PathVariable UUID uuid) {
        ProductDTO product = productService.getProduct(uuid);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping(value = "/{uuid}/price-quote", produces = "application/json")
    ResponseEntity<ProductPriceQuoteDTO> getProductPriceQuote(@PathVariable UUID uuid) {
        logger.info("\nfetching price quote for product :- \n" + uuid.toString() + " \n");
        ProductPriceQuoteDTO quote = productService.getProductPriceQuote(uuid);
        logger.info("\nprice quote for product :- \n" + uuid.toString() + " \n" + quote.toString() + " \n");
        return new ResponseEntity<>(quote, HttpStatus.OK);
    }

    private void validateProductCreationParams(ProductCreateParams params) {
        if(StringUtils.isEmpty(params.getName()))
            throw new IllegalArgumentException("Product Name must be present");
        if(params.getCategoryUuid() == null)
            throw new IllegalArgumentException("Category UUID must be present");
        if(params.getBasePrice() != null && params.getBasePrice() < 0)
            throw new IllegalArgumentException("Product price must be positive");
    }

}
