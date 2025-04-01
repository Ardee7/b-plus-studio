package com.api.b_plus_studio.dtos;

import com.api.b_plus_studio.enums.Role;
import com.api.b_plus_studio.enums.UserStatus;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {

    @Email
    private String email;

    private String username;

    private String firstname;

    private String lastname;

    private String password; // Plain text (hashed before saving)

    private String oauthProvider;

    private String oauthId;

    private String profilePicture;

    private Role role;

    private UserStatus status;
}
