package com.intuit.ordermanagementsystem.controllers;

import com.intuit.ordermanagementsystem.models.VendorProductRelation;
import com.intuit.ordermanagementsystem.models.request.VendorProductRelationCreateParams;
import com.intuit.ordermanagementsystem.models.response.VendorProductRelationDTO;
import com.intuit.ordermanagementsystem.services.VendorProductRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("vendor-product-relations")
public class VendorProductRelationController {

    @Autowired
    private VendorProductRelationService vendorProductRelationService;

    @PostMapping(produces = "application/json")
    ResponseEntity<VendorProductRelationDTO> createVendorProductRelation(@RequestBody VendorProductRelationCreateParams vendorProductRelationCreateParams) {
        validateVendorProductRelationCreationParams(vendorProductRelationCreateParams);
        VendorProductRelationDTO relation = vendorProductRelationService.createRelation(vendorProductRelationCreateParams);
        return new ResponseEntity<>(relation, HttpStatus.CREATED);
    }

    private void validateVendorProductRelationCreationParams(VendorProductRelationCreateParams params) {
        if(StringUtils.isEmpty(params.getVendorUuid()))
            throw new IllegalArgumentException("Vendor UUID must be present");
        if(params.getVendorPrice() == null)
            throw new IllegalArgumentException("price must be present");
        if(params.getTaxSlab() == null)
            throw new IllegalArgumentException("Tax slab must be present");
        if(params.getAvailableQuantity() == null && params.getAvailableQuantity() >= 0)
            throw new IllegalArgumentException("quantity must be present");
        if(params.getStatus() == null)
            throw new IllegalArgumentException("Status must be present");
        if(StringUtils.isEmpty(params.getVendorOriginAddressUuid()))
            throw new IllegalArgumentException("Vendor address UUID must be present");
        if(StringUtils.isEmpty(params.getProductUuid()))
            throw new IllegalArgumentException("Product UUID must be present");
    }
}

//todo update call


