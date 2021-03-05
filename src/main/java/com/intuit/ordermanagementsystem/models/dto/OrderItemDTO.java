package com.intuit.ordermanagementsystem.models.dto;

import com.intuit.ordermanagementsystem.models.OrderItem;
import com.intuit.ordermanagementsystem.models.VendorProductRelation;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OrderItemDTO {

    public OrderItemDTO(OrderItem orderItem) {
        this.uuid = orderItem.getUuid();
        this.createdAt = orderItem.getCreatedAt();
        this.updatedAt = orderItem.getUpdatedAt();
        this.product = new ProductDTO(orderItem.getProduct());
        this.quantity = orderItem.getQuantity();
        this.vendorUuid = orderItem.getVendorUuid();
        this.price = orderItem.getPrice();
        this.status = orderItem.getStatus();
        this.taxSlab = orderItem.getTaxSlab();
        this.originAddressUuid = orderItem.getOriginAddressUuid();
    }
    private UUID uuid;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ProductDTO product;
    private Double quantity;
    private UUID vendorUuid;
    private Double price;
    private OrderItem.OrderItemStatus status;
    private VendorProductRelation.TaxSlab taxSlab;
    private UUID originAddressUuid;

}
