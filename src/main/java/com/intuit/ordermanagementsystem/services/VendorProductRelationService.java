package com.intuit.ordermanagementsystem.services;

import com.intuit.ordermanagementsystem.models.Product;
import com.intuit.ordermanagementsystem.models.request.VendorProductRelationCreateParams;
import com.intuit.ordermanagementsystem.models.dto.VendorProductRelationDTO;
import com.intuit.ordermanagementsystem.models.request.VendorProductRelationUpdateParams;

import java.util.UUID;

public interface VendorProductRelationService {

    VendorProductRelationDTO createRelation(VendorProductRelationCreateParams params);

    VendorProductRelationDTO updateRelation(UUID uuid, VendorProductRelationUpdateParams params);

    VendorProductRelationDTO getAvailableRelationByProductVendorAndOrigin(Product product, UUID vendorUuid, UUID vendorOriginAddressUuid);

    VendorProductRelationDTO getRelationWithLowestProductPrice(Product product);

}
