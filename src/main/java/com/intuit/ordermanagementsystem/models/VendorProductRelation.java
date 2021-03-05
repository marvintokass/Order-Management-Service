package com.intuit.ordermanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;


@Data
@Entity
@Table(name = "vendor_product_relations")
public class VendorProductRelation {

    public enum VendorProductRelationStatus {
        DISABLED, OUT_OF_STOCK
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

    @Column(name = "vendor_uuid")
    private UUID vendorUuid;

    @Column(name = "vendor_price")
    private double vendorPrice;

    @Column(name = "available_quantity")
    private double availableQuantity;

    @Enumerated(EnumType.STRING)
    private VendorProductRelationStatus status;

    @Column(name = "vendor_origin_address_uuid")
    private UUID vendorOriginAddressUuid;

}
