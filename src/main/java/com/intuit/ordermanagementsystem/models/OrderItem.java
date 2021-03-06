package com.intuit.ordermanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.intuit.ordermanagementsystem.models.request.OrderItemParams;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items",
        uniqueConstraints = @UniqueConstraint(columnNames={"order_uuid", "product_uuid"}),
        indexes = {
                @Index(name = "order_item_order_index",  columnList="order_uuid"),
                @Index(name = "order_item_vendor_index",  columnList="vendor_uuid"),
                @Index(name = "order_item_product_index",  columnList="product_uuid")
        }

    )
public class OrderItem {

    public enum OrderItemStatus {
        RETURNED, ORDERED, CANCELLED, DELIVERED, IN_PROGRESS
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
    @NotNull
    @JoinColumn(name = "product_uuid", referencedColumnName = "uuid")
    private Product product;

    @NotNull
    private Double quantity;

    @NotNull
    @Column(name = "vendor_uuid")
    private UUID vendorUuid;

    @NotNull
    private Double price;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(columnDefinition = "VARCHAR(255) default 'ORDERED'")
    private OrderItemStatus status = OrderItemStatus.ORDERED;

    @Column(name = "tax_slab")
    @NotNull
    @Enumerated(EnumType.STRING)
    private VendorProductRelation.TaxSlab taxSlab;

    @Column(name = "origin_address_uuid")
    @NotNull
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
