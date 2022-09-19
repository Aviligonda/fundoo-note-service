package com.bridgelabz.fundoonoteservice.util;

import com.bridgelabz.fundoonoteservice.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private int statusCode;
    private String statusMessage;
    private UserDTO object;
}
