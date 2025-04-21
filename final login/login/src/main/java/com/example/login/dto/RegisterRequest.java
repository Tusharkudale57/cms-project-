package com.example.login.dto;

import com.example.login.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    

    private String username;
    

    private String password;
    

    private Role role;
}
