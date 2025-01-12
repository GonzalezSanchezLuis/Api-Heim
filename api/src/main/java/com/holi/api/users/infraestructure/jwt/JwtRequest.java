package com.holi.api.users.infraestructure.jwt;

import lombok.Data;

@Data
public class JwtRequest {
    private String email;
    private String password;
}
