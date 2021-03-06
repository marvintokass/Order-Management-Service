package com.intuit.ordermanagementsystem.listeners;

import com.intuit.ordermanagementsystem.models.Order;
import com.intuit.ordermanagementsystem.models.OrderItem;
import com.intuit.ordermanagementsystem.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

@Component
public class OrderItemListener {

    @Autowired
    private OrderRepository orderRepository;

    @PostPersist
    @PostUpdate
    @PostRemove
    private void afterAnyUpdate(OrderItem orderItem) {
        Order order = orderItem.getOrder();

        Double totalAmount = 0.0;
        for(OrderItem oi : order.getOrderItems()) {
            totalAmount += (oi.getPrice() * oi.getQuantity());
        }
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);
    }
}
