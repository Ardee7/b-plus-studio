package com.api.b_plus_studio.repositories;

import com.api.b_plus_studio.entities.User;
import com.api.b_plus_studio.enums.Role;
import com.api.b_plus_studio.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByOauthId(String oauthId);
    Page<User> findAllByRoleAndStatusNot(Role role, UserStatus status, Pageable pageable);
}
