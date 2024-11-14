package com.holi.api.drivers.application.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DriverResponse {
    private Long driverId;
    private String fullName;
    private String email;
    private String password;
    private int  document;
    private int  phone;
    private int  licenseNumber;
    private String  vehicleType;
    private String role;
    private LocalDateTime createdAt;
    private boolean isActive;
}
