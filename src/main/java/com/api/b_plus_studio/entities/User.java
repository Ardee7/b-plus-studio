package com.api.b_plus_studio.entities;

import com.api.b_plus_studio.enums.Role;
import com.api.b_plus_studio.enums.UserStatus;
import jakarta.persistence.*;
        import lombok.*;
        import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "oauth_id")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    private String passwordHash; // Nullable for SSO users

    private String oauthProvider; // GOOGLE, FACEBOOK, etc.

    private String oauthId; // Unique provider-specific user ID

    @Column(nullable = false)
    private Role role;

    private String profilePicture;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime lastLogin;

    @Column(nullable = false)
    private UserStatus status = UserStatus.ACTIVE; // ACTIVE, INACTIVE, BANNED, etc.
}