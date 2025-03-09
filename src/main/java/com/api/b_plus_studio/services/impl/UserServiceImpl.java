package com.api.b_plus_studio.services.impl;

import com.api.b_plus_studio.entities.User;
import com.api.b_plus_studio.repositories.UserRepository;
import com.api.b_plus_studio.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
    }
}