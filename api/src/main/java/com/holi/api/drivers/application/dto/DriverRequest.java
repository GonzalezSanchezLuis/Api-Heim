package com.holi.api.drivers.application.dto;

import lombok.Data;
@Data
public class DriverRequest {
    private String fullName;
    private String email;
    private String password;
    private int  document;
    private int  phone;
    private int  licenseNumber;
    private String  vehicleType;
    private String role;
}
