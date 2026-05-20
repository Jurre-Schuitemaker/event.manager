package com.snow.event.manager.user.dto;

import com.snow.event.manager.security.permissions.Role;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignRoleRequset
{
    @NotBlank
    private Role role;
}
