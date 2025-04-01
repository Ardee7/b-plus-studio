package com.api.b_plus_studio.dtos;

import com.api.b_plus_studio.enums.Role;
import com.api.b_plus_studio.enums.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    private Long id;
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
