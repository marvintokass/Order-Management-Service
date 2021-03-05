package com.intuit.ordermanagementsystem.services;

import com.intuit.ordermanagementsystem.models.request.VendorProductRelationCreateParams;
import com.intuit.ordermanagementsystem.models.dto.VendorProductRelationDTO;

public interface VendorProductRelationService {

    VendorProductRelationDTO createRelation(VendorProductRelationCreateParams params);

}
