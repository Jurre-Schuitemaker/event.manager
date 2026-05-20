package com.snow.event.manager.auth.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

@Getter
@Setter
public class LoginRequest
{
    @Email
    @NotBlank
    private String email;
    
    @NotBlank
    private String password;
}
