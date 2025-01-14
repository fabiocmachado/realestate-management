package com.realestate.dto;

import com.realestate.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentDTO {

    private Long id;
    private String name;
    private String cpf;
    private String rg;
    private String email;
    private String phone;

    private String street;
    private String block;
    private String lot;
    private String complement;
    private String number;
    private String neighborhood;
    private String city;
    private String state;

    private String licenseNumber;
    private UserRole role;
    private String password;

    private Boolean hasAdminPermissions;

    private List<Long> prospectedProperties;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
