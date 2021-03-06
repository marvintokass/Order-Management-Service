package com.intuit.ordermanagementsystem.models.request;

import com.intuit.ordermanagementsystem.models.VendorProductRelation;
import lombok.Data;
import java.util.UUID;

@Data
public class VendorProductRelationUpdateParams {

    UUID relationUuid;
    Double vendorPrice;
    VendorProductRelation.TaxSlab taxSlab;
    Double availableQuantity;
    VendorProductRelation.VendorProductRelationStatus status;
    UUID vendorOriginAddressUuid;

}
