package com.intuit.ordermanagementsystem.services;

import com.intuit.ordermanagementsystem.models.VendorProductRelation;
import com.intuit.ordermanagementsystem.models.request.VendorProductRelationCreateParams;

public interface VendorProductRelationService {
    VendorProductRelation createRelation(VendorProductRelationCreateParams params);
}
