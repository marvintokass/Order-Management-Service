package com.intuit.ordermanagementsystem.controllers;

import com.intuit.ordermanagementsystem.models.VendorProductRelation;
import com.intuit.ordermanagementsystem.models.request.VendorProductRelationCreateParams;
import com.intuit.ordermanagementsystem.models.dto.VendorProductRelationDTO;
import com.intuit.ordermanagementsystem.models.request.VendorProductRelationUpdateParams;
import com.intuit.ordermanagementsystem.services.VendorProductRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping(value="/{uuid}", produces = "application/json")
    ResponseEntity<VendorProductRelationDTO> updateVendorProductRelation(@PathVariable UUID uuid, @RequestBody VendorProductRelationUpdateParams vendorProductRelationUpdateParams) {
        validateVendorProductRelationUpdateParams(uuid, vendorProductRelationUpdateParams);
        VendorProductRelationDTO relation = vendorProductRelationService.updateRelation(uuid, vendorProductRelationUpdateParams);
        return new ResponseEntity<>(relation, HttpStatus.OK);
    }

    private void validateVendorProductRelationCreationParams(VendorProductRelationCreateParams params) {
        if(StringUtils.isEmpty(params.getVendorUuid()))
            throw new IllegalArgumentException("Vendor UUID must be present");
        if(params.getVendorPrice() == null || params.getVendorPrice() <= 0)
            throw new IllegalArgumentException("Valid price must be present");
        if(params.getTax() == null)
            throw new IllegalArgumentException("Tax slab must be present");
        try {
            params.setTaxSlab(VendorProductRelation.TaxSlab.valueOf(params.getTax()));
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid Tax Slab");
        }
        if(params.getRelationStatus() == null)
            throw new IllegalArgumentException("Status must must be present");
        try {
            params.setStatus(VendorProductRelation.VendorProductRelationStatus.valueOf(params.getRelationStatus()));
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid relation status");
        }
        if(params.getAvailableQuantity() == null || params.getAvailableQuantity() < 0)
            throw new IllegalArgumentException("Valid quantity must be present");
        if(StringUtils.isEmpty(params.getVendorOriginAddressUuid()))
            throw new IllegalArgumentException("Vendor address UUID must be present");
        if(StringUtils.isEmpty(params.getProductUuid())) {
            throw new IllegalArgumentException("Product UUID must be present");
        }
    }

    private void validateVendorProductRelationUpdateParams(UUID uuid, VendorProductRelationUpdateParams params) {
        if(uuid == null)
            throw new IllegalArgumentException("UUID must be present");
        if(params.getTax() != null)
            params.setTaxSlab(VendorProductRelation.TaxSlab.valueOf(params.getTax()));
        if(params.getVendorPrice() != null && params.getVendorPrice() < 0 )
            throw new IllegalArgumentException("Vendor price is not correct");
        if(params.getAvailableQuantity() != null && params.getAvailableQuantity() < 0)
            throw new IllegalArgumentException("Quantity cannot be less than 0");
        if(params.getAvailableQuantity() != null && params.getAvailableQuantity() == 0)
            params.setStatus(VendorProductRelation.VendorProductRelationStatus.OUT_OF_STOCK);
        else
            params.setStatus(VendorProductRelation.VendorProductRelationStatus.AVAILABLE);
    }

}

//todo UUID check



