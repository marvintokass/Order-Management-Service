package com.intuit.ordermanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;


@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

    public enum OrderItemStatus {
        RETURNED, ORDERED
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
    private Date updatedAt;

    @Column(name = "product_uuid")
    private UUID productUuid;

    private double quantity;

    @Column(name = "vendor_uuid")
    private UUID vendorUuid;

    private double price;

    private OrderItemStatus status;

    @Column(name = "tax_slab")
    @Enumerated(EnumType.STRING)
    private Product.TaxSlab taxSlab;

    @Column(name = "origin_address_uuid")
    private UUID originAddressUuid;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_uuid", referencedColumnName = "uuid")
    private Order order;

}
