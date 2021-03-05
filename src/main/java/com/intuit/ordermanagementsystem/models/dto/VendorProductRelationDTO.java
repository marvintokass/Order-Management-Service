package com.intuit.ordermanagementsystem.models.dto;

import com.intuit.ordermanagementsystem.models.VendorProductRelation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorProductRelationDTO {

    public VendorProductRelationDTO(VendorProductRelation relation) {
        this.uuid = relation.getUuid();
        this.createdAt = relation.getCreatedAt();
        this.updatedAt = relation.getUpdatedAt();
        this.vendorUuid = relation.getVendorUuid();
        this.vendorPrice = relation.getVendorPrice();
        this.taxSlab = relation.getTaxSlab();
        this.availableQuantity = relation.getAvailableQuantity();
        this.status = relation.getStatus();
        this.vendorOriginAddressUuid = relation.getVendorOriginAddressUuid();
    }

    private UUID uuid;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID vendorUuid;
    private Double vendorPrice;
    private VendorProductRelation.TaxSlab taxSlab;
    private Double availableQuantity;
    private VendorProductRelation.VendorProductRelationStatus status;
    private UUID vendorOriginAddressUuid;
    private String vendorName;
}
