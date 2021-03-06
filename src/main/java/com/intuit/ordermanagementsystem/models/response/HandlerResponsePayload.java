package com.intuit.ordermanagementsystem.models.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HandlerResponsePayload {
    int status;
    LocalDateTime timestamp;
    String title;
    List<ErrorPayload> errors;
}
