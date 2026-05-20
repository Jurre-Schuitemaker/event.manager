package com.snow.event.manager.user.service;

import org.springframework.stereotype.Service;

import com.snow.event.manager.user.dto.UpdateUserRequest;
import com.snow.event.manager.user.dto.UserResponse;
import com.snow.event.manager.user.entity.User;
import com.snow.event.manager.user.mapper.UserMapper;
import com.snow.event.manager.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService 
{
    private final UserRepository userRepository;

    public UserResponse getUser(Long id)
    {
        User user = userRepository.findById(id)
            .orElseThrow(() ->
                new RuntimeException("User not found"));

        return UserMapper.toResponse(user);
    }

    public UserResponse updateUser(Long id, UpdateUserRequest request)
    {
        User user = userRepository.findById(id)
            .orElseThrow(() ->
                new RuntimeException("User not found"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        User updatedUser = userRepository.save(user);

        return UserMapper.toResponse(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id, User currentUser)
    {
        if (!id.equals(currentUser.getId()) && !currentUser.isAdmin()) {
            throw new RuntimeException("You can only delete your own account");
        }

        userRepository.deleteById(id);
    }
}
