package com.intuit.ordermanagementsystem.models.request;

import com.intuit.ordermanagementsystem.models.Product;
import com.intuit.ordermanagementsystem.models.VendorProductRelation;
import lombok.Data;
import java.util.UUID;

@Data
public class VendorProductRelationCreateParams {

    UUID vendorUuid;
    Double vendorPrice;
    String tax;
    VendorProductRelation.TaxSlab taxSlab;
    Double availableQuantity;
    String relationStatus;
    VendorProductRelation.VendorProductRelationStatus status;
    UUID vendorOriginAddressUuid;
    UUID productUuid;
    Product product;

}
