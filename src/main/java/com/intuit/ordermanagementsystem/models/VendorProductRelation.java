package com.intuit.ordermanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.intuit.ordermanagementsystem.models.request.VendorProductRelationCreateParams;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vendor_product_relations",
        indexes = {
                @Index(name = "relation_vendor_index",  columnList="vendor_uuid"),
                @Index(name = "relation_product_index", columnList="product_uuid"),
                @Index(name = "relation_address_index", columnList="vendor_origin_address_uuid")
            }
        )
public class VendorProductRelation {

    public enum VendorProductRelationStatus {
        AVAILABLE, DISABLED, OUT_OF_STOCK
    }

    public enum TaxSlab {
        FIVE, TWELVE, EIGHTEEN, TWENTY_EIGHT, THREE, POINT_TWO_FIVE
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

    @Column(name = "vendor_uuid")
    @NotNull
    private UUID vendorUuid;

    @Column(name = "vendor_price")
    @NotNull
    private Double vendorPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "tax_slab")
    @NotNull
    private TaxSlab taxSlab;

    @Column(name = "available_quantity")
    @NotNull
    private Double availableQuantity;

    @Enumerated(EnumType.STRING)
    @NotNull
    private VendorProductRelationStatus status;

    @Column(name = "vendor_origin_address_uuid")
    @NotNull
    private UUID vendorOriginAddressUuid;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_uuid", referencedColumnName = "uuid")
    private Product product;

    public VendorProductRelation(VendorProductRelationCreateParams params) {
        this.availableQuantity = params.getAvailableQuantity();
        this.status = params.getStatus();
        this.vendorOriginAddressUuid = params.getVendorOriginAddressUuid();
        this.vendorPrice = params.getVendorPrice();
        this.vendorUuid = params.getVendorUuid();
        this.taxSlab = params.getTaxSlab();
        this.product = params.getProduct();
    }

}
