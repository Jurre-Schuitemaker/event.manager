package com.snow.event.manager.auth.dto;

import com.snow.event.manager.security.permissions.Role;

import lombok.Getter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest
{
    @NotBlank
    private String firstName;
    
    @NotBlank
    private String lastName;
    
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    private String password;

    @NotBlank
    private Role role;
}
