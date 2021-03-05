package com.intuit.ordermanagementsystem.models.request;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.intuit.ordermanagementsystem.models.Product;
import com.intuit.ordermanagementsystem.models.VendorProductRelation;
import lombok.Data;

@Data
public class ProductCreateParams {
    String name;
    ObjectNode details;
    Product.ProductStatus status;
}
