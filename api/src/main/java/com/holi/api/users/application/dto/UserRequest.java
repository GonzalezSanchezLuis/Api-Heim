package com.holi.api.users.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequest {
    private String fullName;
    private String email;
    private String password;
    private String  document;
    private String phone;
    private String role;

}
