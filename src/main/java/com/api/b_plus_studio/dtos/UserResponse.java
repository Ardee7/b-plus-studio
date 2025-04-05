package com.api.b_plus_studio.dtos;

import com.api.b_plus_studio.enums.Role;
import com.api.b_plus_studio.enums.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class UserResponse {
    private UUID id;
    private String email;
    private String username;
    private String firstname;
    private String lastname;
    private String profilePicture;
    private Role role;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
