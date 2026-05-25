package com.snow.event.manager.user.mapper;

import com.snow.event.manager.user.dto.UserResponse;
import com.snow.event.manager.user.entity.User;

public class UserMapper
{
    public static UserResponse toResponse(User user)
    {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
