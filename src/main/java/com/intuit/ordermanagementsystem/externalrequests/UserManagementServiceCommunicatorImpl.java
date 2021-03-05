package com.intuit.ordermanagementsystem.externalrequests;

import com.intuit.ordermanagementsystem.models.response.UserResponseDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Component
public class UserManagementServiceCommunicatorImpl implements UserManagementServiceCommunicator {

    public List<UserResponseDTO> getUserDetails(List<UUID> userIds) {
        List<UserResponseDTO> users = new ArrayList<>();
        for(UUID userId : userIds) {
            users.add(new UserResponseDTO("marvin", "tokas.marvin@gmail.com", "9999999999", userId));
        }
        return users;
    }

}
