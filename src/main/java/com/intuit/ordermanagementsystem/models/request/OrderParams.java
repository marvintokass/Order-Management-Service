package com.intuit.ordermanagementsystem.models.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import java.util.UUID;

@Data
public class OrderParams {

    UUID deliveryAddressUuid;
    @JsonFormat(pattern="dd/MM/yyyy")
    Date deliveryDate;
    UUID buyerUuid;

}
