package com.intuit.ordermanagementsystem.models.response;

import lombok.Data;

import java.util.List;

@Data
public class UserManagementServiceResponse {
    List<UserResponseDTO> users;
}
