package com.intuit.ordermanagementsystem.controllers;

import com.intuit.ordermanagementsystem.models.Product;
import com.intuit.ordermanagementsystem.models.request.ProductCreateParams;
import com.intuit.ordermanagementsystem.models.response.ProductPriceQuote;
import com.intuit.ordermanagementsystem.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(produces = "application/json")
    ResponseEntity<Product> createProduct(@RequestBody ProductCreateParams productCreateParams) {
        Product product = productService.createProduct(productCreateParams);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping(value = "/{uuid}", produces = "application/json")
    ResponseEntity<Product> getProduct(@PathVariable UUID uuid) {
        Product product = productService.getProduct(uuid);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping(value = "/{uuid}/price-quote", produces = "application/json")
    ResponseEntity<ProductPriceQuote> getProductPriceQuote(@PathVariable UUID uuid) {
        ProductPriceQuote quote = productService.getProductPriceQuote(uuid);
        return new ResponseEntity<>(quote, HttpStatus.OK);
    }
}
