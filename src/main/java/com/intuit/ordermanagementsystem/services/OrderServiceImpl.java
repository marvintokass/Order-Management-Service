package com.intuit.ordermanagementsystem.services;

import com.intuit.ordermanagementsystem.exceptions.ResourceNotFoundException;
import com.intuit.ordermanagementsystem.models.Order;
import com.intuit.ordermanagementsystem.models.Product;
import com.intuit.ordermanagementsystem.models.VendorProductRelation;
import com.intuit.ordermanagementsystem.models.request.OrderCreateParams;
import com.intuit.ordermanagementsystem.models.request.OrderItemParams;
import com.intuit.ordermanagementsystem.models.dto.OrderDTO;
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
    private VendorProductRelationRepository vendorProductRelationRepository;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public OrderDTO createOrder(OrderCreateParams params) {
        saveProductInOrderItemParams(params);
        Order order = new Order(params);
        orderRepository.save(order);
        return new OrderDTO(order);
    }

    private void saveProductInOrderItemParams(OrderCreateParams params) {
        for (OrderItemParams orderItemParams : params.getOrderItemParams()) {
            Optional<Product> optionalProduct = productRepository.findById(orderItemParams.getProductUuid());
            if(!optionalProduct.isPresent())
                throw new ResourceAccessException("Product not found with UUID: " + orderItemParams.getProductUuid());
            updateVendorProductRelationForProduct(optionalProduct.get(), orderItemParams);
            orderItemParams.setProduct(optionalProduct.get());
        }
    }

    private void updateVendorProductRelationForProduct(Product product, OrderItemParams params) {
        Optional<VendorProductRelation> optionalRelation = vendorProductRelationRepository.findFirstByProductAndVendorUuidAndVendorOriginAddressUuid(product, params.getVendorUuid(), params.getOriginAddressUuid());
        if(!optionalRelation.isPresent())
            throw new ResourceNotFoundException("Vendor Product Relation not found");
        VendorProductRelation relation = optionalRelation.get();
        if(relation.getAvailableQuantity() < params.getQuantity())
            throw new RuntimeException("Cannot create order for quantity more than available quantity with vendor");
        relation.setAvailableQuantity(relation.getAvailableQuantity() - params.getQuantity());
        if(relation.getAvailableQuantity() == 0)
            relation.setStatus(VendorProductRelation.VendorProductRelationStatus.OUT_OF_STOCK);
        updatePriceInParams(params, relation);
        vendorProductRelationRepository.save(relation);
    }

    private void updatePriceInParams(OrderItemParams params, VendorProductRelation relation) {
        params.setPrice(relation.getVendorPrice());
        params.setTaxSlab(relation.getTaxSlab());
    }

}

