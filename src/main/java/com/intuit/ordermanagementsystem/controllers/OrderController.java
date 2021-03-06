package com.intuit.ordermanagementsystem.controllers;

import com.intuit.ordermanagementsystem.models.request.OrderCreateParams;
import com.intuit.ordermanagementsystem.models.request.OrderItemParams;
import com.intuit.ordermanagementsystem.models.request.OrderParams;
import com.intuit.ordermanagementsystem.models.dto.OrderDTO;
import com.intuit.ordermanagementsystem.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(produces = "application/json")
    ResponseEntity<OrderDTO> createOrder(@RequestBody OrderCreateParams params) {
        validateOrderCreateParams(params);
        OrderDTO order = orderService.createOrder(params);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "orders/" + order.getUuid());
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    private void validateOrderCreateParams(OrderCreateParams params) {
        if(params.getOrderParams() == null)
            throw new IllegalArgumentException("Order Params must be present");
        if(params.getOrderItemParams() == null || params.getOrderItemParams().isEmpty())
            throw new IllegalArgumentException("Order Item Params must be present");
        validateOrderParams(params.getOrderParams());
        validateOrderItemCreateParams(params.getOrderItemParams());
    }

    private void validateOrderParams(OrderParams params) {
        if(params.getDeliveryAddressUuid() == null)
            throw new IllegalArgumentException("Delivery address must be present");
        if(params.getDeliveryDate() == null)
            throw new IllegalArgumentException("Delivery Date must be present");
        if(params.getBuyerUuid() == null)
            throw new IllegalArgumentException("Buyer UUID must be present");
    }

    private void validateOrderItemCreateParams(List<OrderItemParams> params) {
        for (OrderItemParams param : params) {
            if(param.getProductUuid() == null)
                throw new IllegalArgumentException("Product UUID must be present");
            if(param.getQuantity() == null  || param.getQuantity() <= 0)
                throw new IllegalArgumentException("Quantity must be present and should not be negative");
            if(param.getVendorUuid() == null)
                throw new IllegalArgumentException("Vendor UUID must be present");
            if(param.getOriginAddressUuid() == null)
                throw new IllegalArgumentException("Origin Address UUID must be present");
        }
    }

}
