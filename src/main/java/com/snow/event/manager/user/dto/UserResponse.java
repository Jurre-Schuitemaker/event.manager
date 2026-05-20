package com.snow.event.manager.user.dto;

import java.time.LocalDateTime;

import com.snow.event.manager.security.permissions.Role;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse
{
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Role role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
