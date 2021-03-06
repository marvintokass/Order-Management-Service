package com.intuit.ordermanagementsystem.services;

import com.intuit.ordermanagementsystem.exceptions.ResourceNotFoundException;
import com.intuit.ordermanagementsystem.models.Order;
import com.intuit.ordermanagementsystem.models.Product;
import com.intuit.ordermanagementsystem.models.VendorProductRelation;
import com.intuit.ordermanagementsystem.models.dto.VendorProductRelationDTO;
import com.intuit.ordermanagementsystem.models.request.OrderCreateParams;
import com.intuit.ordermanagementsystem.models.request.OrderItemParams;
import com.intuit.ordermanagementsystem.models.dto.OrderDTO;
import com.intuit.ordermanagementsystem.models.request.VendorProductRelationUpdateParams;
import com.intuit.ordermanagementsystem.repositories.OrderRepository;
import com.intuit.ordermanagementsystem.repositories.ProductRepository;
import com.intuit.ordermanagementsystem.repositories.VendorProductRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private VendorProductRelationService vendorProductRelationService;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public OrderDTO createOrder(OrderCreateParams params) {
        saveProductInOrderItemParams(params);
        updateVendorProductRelations(params);
        Order order = new Order(params);
        orderRepository.save(order);
        return new OrderDTO(order);
    }

    private void saveProductInOrderItemParams(OrderCreateParams params) {
        for (OrderItemParams orderItemParams : params.getOrderItemParams()) {
            Optional<Product> optionalProduct = productRepository.findById(orderItemParams.getProductUuid());
            if(!optionalProduct.isPresent())
                throw new ResourceAccessException("Product not found with UUID: " + orderItemParams.getProductUuid());
            orderItemParams.setProduct(optionalProduct.get());
        }
    }

    public void updateVendorProductRelations(OrderCreateParams params) {
        for(OrderItemParams orderItemParams : params.getOrderItemParams()) {
            VendorProductRelationDTO relation = vendorProductRelationService.getRelationByProductVendorAndOrigin(orderItemParams.getProduct(), orderItemParams.getVendorUuid(), orderItemParams.getOriginAddressUuid());
            if (relation.getAvailableQuantity() < orderItemParams.getQuantity())
                throw new RuntimeException("Cannot create order for quantity more than available quantity with vendor");
            updatePriceInParams(orderItemParams, relation);
            VendorProductRelationUpdateParams relationUpdateParams = new VendorProductRelationUpdateParams();
            relationUpdateParams.setAvailableQuantity(relation.getAvailableQuantity() - orderItemParams.getQuantity());
            relationUpdateParams.setStatus(relation.getAvailableQuantity() == 0 ? VendorProductRelation.VendorProductRelationStatus.OUT_OF_STOCK : null);
            vendorProductRelationService.updateRelation(relation.getUuid(), relationUpdateParams);
        }
    }

    private void updatePriceInParams(OrderItemParams params, VendorProductRelationDTO relation) {
        params.setPrice(relation.getVendorPrice());
        params.setTaxSlab(relation.getTaxSlab());
    }

}

