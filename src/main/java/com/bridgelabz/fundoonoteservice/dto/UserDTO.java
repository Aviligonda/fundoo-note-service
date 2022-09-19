package com.bridgelabz.fundoonoteservice.dto;

import lombok.Data;

import javax.persistence.Lob;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
public class UserDTO {
    private Long id;
    private String name;
    private String emailId;
    private String password;
    private boolean isActive;
    private boolean isDeleted;
    private LocalDate dateOfBirth;
    private long phoneNumber;
    @Lob
    private byte[] profilePic;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

}
