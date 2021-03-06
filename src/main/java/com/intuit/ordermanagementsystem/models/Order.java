package com.intuit.ordermanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.intuit.ordermanagementsystem.models.request.OrderCreateParams;
import com.intuit.ordermanagementsystem.models.request.OrderItemParams;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders",
        indexes = {
                @Index(name = "order_buyer_index",  columnList="buyer_uuid")
            }
        )
public class Order {

    public enum OrderStatus {
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

    @NotNull
    @Column(name = "delivery_address_uuid")
    private UUID deliveryAddressUuid;


    @NotNull
    @Column(columnDefinition = "VARCHAR(255) default 'ORDERED'")
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.ORDERED;

    @NotNull
    @Column(name = "buyer_uuid")
    private UUID buyerUuid;

    @Column(name = "total_amount")
    private Double totalAmount;

    @NotNull
    @Column(name = "delivery_date")
    private Date deliveryDate;

    @JsonManagedReference
    @NotNull
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    public Order(OrderCreateParams params) {
        this.buyerUuid = params.getOrderParams().getBuyerUuid();
        this.deliveryAddressUuid = params.getOrderParams().getDeliveryAddressUuid();
        this.deliveryDate = params.getOrderParams().getDeliveryDate();
        this.totalAmount = params.getOrderParams().getTotalAmount();
        List<OrderItem> orderItems = new ArrayList<>();
        for(OrderItemParams orderItemParams : params.getOrderItemParams()) {
            orderItemParams.setOrder(this);
            orderItems.add(new OrderItem(orderItemParams));
        }
        this.orderItems = orderItems;
    }

}
