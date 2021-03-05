package com.intuit.ordermanagementsystem.externalrequests;

import com.intuit.ordermanagementsystem.models.response.UserResponseDTO;
import java.util.List;
import java.util.UUID;

public interface UserManagementServiceCommunicator {

    List<UserResponseDTO> getUserDetails(List<UUID> userIds);

}
