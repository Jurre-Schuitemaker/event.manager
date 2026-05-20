package com.snow.event.manager.auth.controller;
 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.snow.event.manager.auth.dto.AuthResponse;
import com.snow.event.manager.auth.dto.LoginRequest;
import com.snow.event.manager.auth.dto.RegisterRequest;
import com.snow.event.manager.auth.service.AuthService;
import com.snow.event.manager.security.permissions.Role;
 
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
 
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController
{
    private final AuthService authService;
 
    @PostMapping("/register/user")
    public AuthResponse registerUser(@Valid @RequestBody RegisterRequest request)
    {
        return authService.register(request, Role.USER);
    }
 
    @PostMapping("/register/organizer")
    public AuthResponse registerOrganizer(@Valid @RequestBody RegisterRequest request)
    {
        return authService.register(request, Role.ORGANIZER);
    }
 
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request)
    {
        return authService.login(request);
    }
}