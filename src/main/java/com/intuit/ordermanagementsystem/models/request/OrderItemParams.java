package com.intuit.ordermanagementsystem.models.request;
import com.intuit.ordermanagementsystem.models.Order;
import com.intuit.ordermanagementsystem.models.Product;
import com.intuit.ordermanagementsystem.models.VendorProductRelation;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemParams {
    UUID productUuid;
    Product product;
    double quantity;
    UUID vendorUuid;
    double price;
    VendorProductRelation.TaxSlab taxSlab;
    UUID originAddressUuid;
    Order order;
}
