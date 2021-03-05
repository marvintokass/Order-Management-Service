package com.intuit.ordermanagementsystem.controllers;

import com.intuit.ordermanagementsystem.models.VendorProductRelation;
import com.intuit.ordermanagementsystem.models.request.VendorProductRelationCreateParams;
import com.intuit.ordermanagementsystem.services.VendorProductRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("vendor-product-relations")
public class VendorProductRelationController {

    @Autowired
    private VendorProductRelationService vendorProductRelationService;

    @PostMapping(produces = "application/json")
    ResponseEntity<VendorProductRelation> createVendorProductRelation(@RequestBody VendorProductRelationCreateParams vendorProductRelationCreateParams) {
        VendorProductRelation relation = vendorProductRelationService.createRelation(vendorProductRelationCreateParams);
        return new ResponseEntity<>(relation, HttpStatus.OK);
    }
}
