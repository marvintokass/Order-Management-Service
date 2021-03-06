package com.intuit.ordermanagementsystem.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorPayload {
    String error;
    String detail;
}
