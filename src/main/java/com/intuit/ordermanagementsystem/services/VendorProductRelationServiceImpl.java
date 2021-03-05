package com.intuit.ordermanagementsystem.services;

import com.intuit.ordermanagementsystem.models.Product;
import com.intuit.ordermanagementsystem.models.VendorProductRelation;
import com.intuit.ordermanagementsystem.models.request.VendorProductRelationCreateParams;
import com.intuit.ordermanagementsystem.repositories.ProductRepository;
import com.intuit.ordermanagementsystem.repositories.VendorProductRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendorProductRelationServiceImpl implements VendorProductRelationService {

    @Autowired
    private VendorProductRelationRepository vendorProductRelationRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public VendorProductRelation createRelation(VendorProductRelationCreateParams params) {
        VendorProductRelation relation = new VendorProductRelation(params);
        Optional<Product> optionalProduct = productRepository.findById(params.getProductUuid());
        if(!optionalProduct.isPresent()) throw new RuntimeException("product not found");
        relation.setProduct(optionalProduct.get());
        vendorProductRelationRepository.save(relation);
        return relation;
    }
}
