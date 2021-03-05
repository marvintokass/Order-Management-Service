package com.intuit.ordermanagementsystem.models.dto;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.intuit.ordermanagementsystem.models.Product;
import com.intuit.ordermanagementsystem.models.VendorProductRelation;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ProductDTO {

    public ProductDTO(Product product) {
        this.uuid = product.getUuid();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
        this.name = product.getName();
        this.basePrice = product.getBasePrice();
        this.details = product.getDetails();
        this.status = product.getStatus();
        this.categoryUuid = product.getCategoryUuid();
        List<VendorProductRelationDTO> relationDTOs = new ArrayList<>();
        for(VendorProductRelation relation : product.getVendorProductRelations()) {
            relationDTOs.add(new VendorProductRelationDTO(relation));
        }
        this.vendorProductRelations = relationDTOs;
    }

    private UUID uuid;
    private UUID categoryUuid;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private Double basePrice;
    private ObjectNode details;
    private Product.ProductStatus status;
    private List<VendorProductRelationDTO> vendorProductRelations;

}
