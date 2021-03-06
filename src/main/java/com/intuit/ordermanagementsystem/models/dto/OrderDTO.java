package com.intuit.ordermanagementsystem.models.dto;

import com.intuit.ordermanagementsystem.models.Order;
import com.intuit.ordermanagementsystem.models.OrderItem;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDTO {

    public OrderDTO(Order order) {
        this.uuid = order.getUuid();
        this.createdAt = order.getCreatedAt();
        this.updatedAt = order.getUpdatedAt();
        this.deliveryAddressUuid = order.getDeliveryAddressUuid();
        this.status = order.getStatus();
        this.buyerUuid = order.getBuyerUuid();
        this.totalAmount = order.getTotalAmount();
        this.deliveryDate = order.getDeliveryDate();
        if(order.getOrderItems() != null) {
            List<OrderItemDTO> orderItemDTOs = new ArrayList<>();
            for (OrderItem orderItem : order.getOrderItems()) {
                orderItemDTOs.add(new OrderItemDTO(orderItem));
            }
            this.orderItems = orderItemDTOs;
        }
    }

    private UUID uuid;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID deliveryAddressUuid;
    private Order.OrderStatus status;
    private UUID buyerUuid;
    private Double totalAmount;
    private Date deliveryDate;
    private List<OrderItemDTO> orderItems;

}
