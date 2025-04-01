package com.api.b_plus_studio.services.impl;

import com.api.b_plus_studio.Exceptions.BaseServiceException;
import com.api.b_plus_studio.Exceptions.UserException;
import com.api.b_plus_studio.Utilities.PojoUtils;
import com.api.b_plus_studio.dtos.CreateUserRequest;
import com.api.b_plus_studio.dtos.ExceptionResponse;
import com.api.b_plus_studio.dtos.UserRequest;
import com.api.b_plus_studio.dtos.UserResponse;
import com.api.b_plus_studio.entities.User;
import com.api.b_plus_studio.enums.Role;
import com.api.b_plus_studio.enums.UserStatus;
import com.api.b_plus_studio.repositories.UserRepository;
import com.api.b_plus_studio.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
    }

    @Transactional
    @Override
    public User createUser(CreateUserRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .passwordHash(request.getPassword() != null ? passwordEncoder.encode(request.getPassword()) : null)
                .oauthProvider(request.getOauthProvider())
                .oauthId(request.getOauthId())
                .profilePicture(request.getProfilePicture())
                .role(request.getRole() != null ? request.getRole() : Role.USER) // Default role
                .status(request.getStatus() != null ? request.getStatus() : UserStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }


    // Get all users
    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get user by ID
    @Override
    public UserResponse getUserById(Long id) throws UserException {
        return userRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new UserException("User Not Found."));
    }

    // Update user
    @Transactional
    @Override
    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUpdatedAt(LocalDateTime.now());
        PojoUtils.copyNonNullProperties(request, user);

        if (request.getPassword() != null) {
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        return toResponse(userRepository.save(user));
    }

    // Delete user
    @Transactional
    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    // Convert User Entity to Response DTO
    private UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .profilePicture(user.getProfilePicture())
                .role(user.getRole())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}