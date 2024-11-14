package com.holi.api.users.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String fullName;
    private String email;
    private String password;
    private String  document;
    private String  phone;
    private String role;
    private LocalDateTime createdAt;
    private boolean isActive;

    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
    }
}
