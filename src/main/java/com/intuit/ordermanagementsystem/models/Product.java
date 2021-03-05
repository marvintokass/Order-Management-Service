package com.intuit.ordermanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.intuit.ordermanagementsystem.models.request.ProductCreateParams;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "products")
public class Product {

    public enum ProductStatus {
        ACTIVE, IN_ACTIVE
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
    private Date updatedAt;

    private String name;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private ObjectNode details;

    @Column(name="base_price")
    private double basePrice;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "tax_slab")
    private TaxSlab taxSlab;

    @JsonManagedReference
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<VendorProductRelation> vendorProductRelations;

    public Product(ProductCreateParams params) {
        this.setBasePrice(params.getBasePrice());
        this.setDetails(params.getDetails());
        this.setName(params.getName());
        this.setStatus(params.getStatus());
        this.setTaxSlab(params.getTaxSlab());
    }
}
