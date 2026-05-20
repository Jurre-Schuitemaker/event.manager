package com.snow.event.manager.user.controller;

import org.springframework.web.bind.annotation.*;

import com.snow.event.manager.user.dto.UpdateUserRequest;
import com.snow.event.manager.user.dto.UserResponse;
import com.snow.event.manager.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController 
{
    private final UserService userService;

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) 
    {
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(
            @PathVariable Long id,

            @Valid
            @RequestBody UpdateUserRequest request
    ) {

        return userService.updateUser(id, request);
    }
}
