package com.intuit.ordermanagementsystem.controllers;

import com.intuit.ordermanagementsystem.models.Order;
import com.intuit.ordermanagementsystem.models.request.OrderCreateParams;
import com.intuit.ordermanagementsystem.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(produces = "application/json")
    ResponseEntity<Order> createOrder(@RequestBody OrderCreateParams params) {
        Order order = orderService.createOrder(params);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
