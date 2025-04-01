package com.api.b_plus_studio.dtos;

import com.api.b_plus_studio.enums.Role;
import com.api.b_plus_studio.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    private String password; // Plain text (hashed before saving)

    private String oauthProvider;

    private String oauthId;

    private String profilePicture;

    private Role role;

    private UserStatus status;
}