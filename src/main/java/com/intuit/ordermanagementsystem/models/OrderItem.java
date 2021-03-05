package com.intuit.ordermanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.intuit.ordermanagementsystem.models.request.OrderCreateParams;
import com.intuit.ordermanagementsystem.models.request.OrderItemParams;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
public class OrderItem {

    public enum OrderItemStatus {
        RETURNED, ORDERED, CANCELLED
    }

    @Id
    @GeneratedValue
    private UUID uuid;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
    @Column(name = "created_at", updatable=false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_uuid", referencedColumnName = "uuid")
    private Product product;

    private double quantity;

    @Column(name = "vendor_uuid")
    private UUID vendorUuid;

    private double price;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "default 'ORDERED'")
    private OrderItemStatus status;

    @Column(name = "tax_slab")
    @Enumerated(EnumType.STRING)
    private VendorProductRelation.TaxSlab taxSlab;

    @Column(name = "origin_address_uuid")
    private UUID originAddressUuid;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "order_uuid", referencedColumnName = "uuid")
    private Order order;

    public OrderItem(OrderItemParams orderItemParams) {
        this.originAddressUuid = orderItemParams.getOriginAddressUuid();
        this.product = orderItemParams.getProduct();
        this.vendorUuid = orderItemParams.getVendorUuid();
        this.price = orderItemParams.getPrice();
        this.quantity = orderItemParams.getQuantity();
        this.taxSlab = orderItemParams.getTaxSlab();
        this.order = orderItemParams.getOrder();
    }
}
