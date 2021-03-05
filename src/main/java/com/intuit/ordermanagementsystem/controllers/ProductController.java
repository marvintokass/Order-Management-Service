package com.intuit.ordermanagementsystem.controllers;

import com.intuit.ordermanagementsystem.models.Product;
import com.intuit.ordermanagementsystem.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping(produces = "application/json")
    ResponseEntity<Product> createProduct() {
        Product p = new Product();
        productRepository.save(p);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }
}
