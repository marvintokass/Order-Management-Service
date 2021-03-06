package com.intuit.ordermanagementsystem.controllers;

import com.intuit.ordermanagementsystem.models.dto.OrderDTO;
import com.intuit.ordermanagementsystem.models.request.OrderCreateParams;
import com.intuit.ordermanagementsystem.models.request.OrderItemParams;
import com.intuit.ordermanagementsystem.models.request.OrderParams;
import com.intuit.ordermanagementsystem.services.OrderService;
import com.intuit.ordermanagementsystem.services.VendorProductRelationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderControllerTests {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private void failCreateWithoutOrderParams(OrderCreateParams params) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderController.createOrder(params);
        });
        assertEquals("Order Params must be present", exception.getMessage());
    }

    private void failOnCreateWithoutOrderItemParams(OrderCreateParams params) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderController.createOrder(params);
        });
        assertEquals("Order Item Params must be present", exception.getMessage());
    }

    private void failCreateWithoutDeliveryAddress(OrderCreateParams params) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderController.createOrder(params);
        });
        assertEquals("Delivery address must be present", exception.getMessage());
    }

    private void failCreateWithoutDeliveryDate(OrderCreateParams params) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderController.createOrder(params);
        });
        assertEquals("Delivery Date must be present", exception.getMessage());
    }

    private void failCreateWithoutBuyerUUID(OrderCreateParams params) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderController.createOrder(params);
        });
        assertEquals("Buyer UUID must be present", exception.getMessage());
    }

    private void failCreateWithoutProductUUID(OrderCreateParams params) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderController.createOrder(params);
        });
        assertEquals("Product UUID must be present", exception.getMessage());
    }

    private void failCreateWithoutValidQuantity(OrderCreateParams params) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderController.createOrder(params);
        });
        assertEquals("Quantity must be present and should not be negative", exception.getMessage());
    }

    private void failCreateWithoutValidVendorUUID(OrderCreateParams params) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderController.createOrder(params);
        });
        assertEquals("Vendor UUID must be present", exception.getMessage());
    }

    private void failCreateWithoutVendorAddressUUID(OrderCreateParams params) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderController.createOrder(params);
        });
        assertEquals("Origin Address UUID must be present", exception.getMessage());
    }


    @Test
    void FailOnInvalidCreateParamsTest() {
        OrderCreateParams params = new OrderCreateParams();

        failCreateWithoutOrderParams(params);
        params.setOrderParams(new OrderParams());

        failOnCreateWithoutOrderItemParams(params);
        params.setOrderItemParams(new ArrayList<>(Arrays.asList(new OrderItemParams())));

        failCreateWithoutDeliveryAddress(params);
        params.getOrderParams().setDeliveryAddressUuid(UUID.randomUUID());

        failCreateWithoutDeliveryDate(params);
        params.getOrderParams().setDeliveryDate(new Date());

        failCreateWithoutBuyerUUID(params);
        params.getOrderParams().setBuyerUuid(UUID.randomUUID());

        failCreateWithoutProductUUID(params);
        params.getOrderItemParams().get(0).setProductUuid(UUID.randomUUID());

        failCreateWithoutValidQuantity(params);
        params.getOrderItemParams().get(0).setQuantity(10.0);

        failCreateWithoutValidVendorUUID(params);
        params.getOrderItemParams().get(0).setVendorUuid(UUID.randomUUID());

        failCreateWithoutVendorAddressUUID(params);
        params.getOrderItemParams().get(0).setOriginAddressUuid(UUID.randomUUID());
    }

    @Test
    void CreateOnValidCreateParamsTest() {
        OrderCreateParams params = new OrderCreateParams();

        params.setOrderParams(new OrderParams());
        params.setOrderItemParams(new ArrayList<>(Arrays.asList(new OrderItemParams())));
        params.getOrderParams().setDeliveryAddressUuid(UUID.randomUUID());
        params.getOrderParams().setDeliveryDate(new Date());
        params.getOrderParams().setBuyerUuid(UUID.randomUUID());
        params.getOrderItemParams().get(0).setProductUuid(UUID.randomUUID());
        params.getOrderItemParams().get(0).setQuantity(10.0);
        params.getOrderItemParams().get(0).setVendorUuid(UUID.randomUUID());
        params.getOrderItemParams().get(0).setOriginAddressUuid(UUID.randomUUID());

        when(orderService.createOrder(params)).thenReturn(new OrderDTO());

        ResponseEntity<OrderDTO> response = orderController.createOrder(params);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

}
