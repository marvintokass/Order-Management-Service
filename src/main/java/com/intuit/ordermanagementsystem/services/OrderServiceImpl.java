package com.intuit.ordermanagementsystem.services;

import com.intuit.ordermanagementsystem.models.Order;
import com.intuit.ordermanagementsystem.models.Product;
import com.intuit.ordermanagementsystem.models.request.OrderCreateParams;
import com.intuit.ordermanagementsystem.models.request.OrderItemParams;
import com.intuit.ordermanagementsystem.repositories.OrderRepository;
import com.intuit.ordermanagementsystem.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Order createOrder(OrderCreateParams params) {
        saveProductInOrderItemParams(params);
        Order order = new Order(params);
        orderRepository.save(order);
        return order;
    }

    private void saveProductInOrderItemParams(OrderCreateParams params) {
        for (OrderItemParams orderItemParams : params.getOrderItemParams()) {
            Optional<Product> optionalProduct = productRepository.findById(orderItemParams.getProductUuid());
            if(!optionalProduct.isPresent())
                throw new RuntimeException("Product not found");
            orderItemParams.setProduct(optionalProduct.get());
        }
    }
}

