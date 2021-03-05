package com.intuit.ordermanagementsystem.services;

import com.intuit.ordermanagementsystem.models.Product;
import com.intuit.ordermanagementsystem.models.VendorProductRelation;
import com.intuit.ordermanagementsystem.models.request.ProductCreateParams;
import com.intuit.ordermanagementsystem.models.response.ProductPriceQuote;
import com.intuit.ordermanagementsystem.repositories.ProductRepository;
import com.intuit.ordermanagementsystem.repositories.VendorProductRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private VendorProductRelationRepository vendorProductRelationRepository;


    @Override
    public Product createProduct(ProductCreateParams params) {
        Product product = new Product(params);
        productRepository.save(product);
        return product;
    }

    @Override
    public Product getProduct(UUID uuid) {
        Optional<Product> optionalProduct = productRepository.findById(uuid);
        if(optionalProduct.isPresent())
            return optionalProduct.get();
        throw new RuntimeException("product not found");
    }

    @Override
    public ProductPriceQuote getProductPriceQuote(UUID uuid) {
        Optional<Product> optionalProduct = productRepository.findById(uuid);
        if(!optionalProduct.isPresent()) throw new RuntimeException("Product not present");
        Product product = optionalProduct.get();
        VendorProductRelation relation = vendorProductRelationRepository.findFirstByProductOrderByVendorPriceAsc(product).get();
        return new ProductPriceQuote(product, relation);
    }
}
