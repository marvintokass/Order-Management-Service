package com.intuit.ordermanagementsystem.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    String name;
    String email;
    String contactNumber;
    UUID uuid;
}
