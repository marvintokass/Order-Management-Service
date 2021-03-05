package com.intuit.ordermanagementsystem.models.request;

import lombok.Data;
import java.util.List;

@Data
public class OrderCreateParams {

    OrderParams orderParams;
    List<OrderItemParams> orderItemParams;

}
