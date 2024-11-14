package com.holi.api.drivers.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "drivers")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Driver {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
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

    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
    }
}
