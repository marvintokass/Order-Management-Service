package com.intuit.ordermanagementsystem.models.request;

import com.intuit.ordermanagementsystem.models.Product;
import com.intuit.ordermanagementsystem.models.VendorProductRelation;
import lombok.Data;

import java.util.UUID;

@Data
public class VendorProductRelationCreateParams {
    UUID vendorUuid;
    double vendorPrice;
    double availableQuantity;
    VendorProductRelation.VendorProductRelationStatus status;
    UUID vendorOriginAddressUuid;
    UUID productUuid;
}
