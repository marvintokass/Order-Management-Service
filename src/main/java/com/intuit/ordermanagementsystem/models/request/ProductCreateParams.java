package com.intuit.ordermanagementsystem.models.request;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.intuit.ordermanagementsystem.models.Product;
import lombok.Data;

@Data
public class ProductCreateParams {
    String name;
    ObjectNode details;
    double basePrice;
    Product.ProductStatus status;
    Product.TaxSlab taxSlab;
}
