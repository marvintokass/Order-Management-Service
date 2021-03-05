package com.intuit.ordermanagementsystem.services;

import com.intuit.ordermanagementsystem.models.Order;
import com.intuit.ordermanagementsystem.models.request.OrderCreateParams;

public interface OrderService {
    Order createOrder(OrderCreateParams params);
}
