package com.intuit.ordermanagementsystem.models.request;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductCreateParams {
    String name;
    Double basePrice;
    ObjectNode details;
    UUID categoryUuid;
}
