package com.api.b_plus_studio.entities;

import com.api.b_plus_studio.Enums.UserStatus;
import jakarta.persistence.*;
        import lombok.*;
        import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Getter
@Setter
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

    private String passwordHash; // Nullable for SSO users

    private String oauthProvider; // GOOGLE, FACEBOOK, etc.

    private String oauthId; // Unique provider-specific user ID

    @Column(nullable = false)
    private String role = "USER";

    private String profilePicture;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime lastLogin;

    @Column(nullable = false)
    private UserStatus status = UserStatus.ACTIVE; // ACTIVE, INACTIVE, BANNED, etc.
}