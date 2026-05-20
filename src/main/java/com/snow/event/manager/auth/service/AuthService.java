package com.snow.event.manager.auth.service;
 
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
 
import com.snow.event.manager.auth.dto.AuthResponse;
import com.snow.event.manager.auth.dto.LoginRequest;
import com.snow.event.manager.auth.dto.RegisterRequest;
import com.snow.event.manager.auth.jwt.JwtService;
import com.snow.event.manager.security.permissions.Role;
import com.snow.event.manager.user.entity.User;
import com.snow.event.manager.user.repository.UserRepository;
 
import lombok.RequiredArgsConstructor;
 
@Service
@RequiredArgsConstructor
public class AuthService 
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
 
    public AuthResponse register(RegisterRequest request, Role role) 
    {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
 
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .enabled(true)
                .build();
 
        userRepository.save(user);
 
        String token = jwtService.generateToken(user.getEmail());
 
        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole().getValue())
                .build();
    }
 
    public AuthResponse login(LoginRequest request)
    {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
 
        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );
 
        if(!matches) 
        {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());
 
        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole().getValue())
                .build();
    }
}