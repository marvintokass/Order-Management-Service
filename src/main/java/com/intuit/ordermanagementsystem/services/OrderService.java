package com.intuit.ordermanagementsystem.services;

import com.intuit.ordermanagementsystem.models.request.OrderCreateParams;
import com.intuit.ordermanagementsystem.models.response.OrderDTO;

public interface OrderService {
    OrderDTO createOrder(OrderCreateParams params);
}
