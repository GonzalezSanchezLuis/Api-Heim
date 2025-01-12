package com.holi.api.users.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {
    private Long userId;
    private String fullName;
    private String email;
    private String phone;
    private String  document;
    private String role;
    private String createdAt;
}
