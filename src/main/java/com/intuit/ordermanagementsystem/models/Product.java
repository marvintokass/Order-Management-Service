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

    private String name;

    @Column(name = "base_price")
    private Double basePrice;

    @Column(name = "category_uuid")
    private UUID categoryUuid;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private ObjectNode details;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255) default 'ACTIVE'")
    private ProductStatus status;

    @JsonManagedReference
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<VendorProductRelation> vendorProductRelations;

    public Product(ProductCreateParams params) {
        this.details = params.getDetails();
        this.basePrice = params.getBasePrice();
        this.name = params.getName();
        this.categoryUuid = params.getCategoryUuid();
    }

}
