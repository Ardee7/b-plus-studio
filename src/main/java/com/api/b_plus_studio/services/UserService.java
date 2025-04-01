package com.api.b_plus_studio.services;

import com.api.b_plus_studio.Exceptions.UserException;
import com.api.b_plus_studio.dtos.CreateUserRequest;
import com.api.b_plus_studio.dtos.UserRequest;
import com.api.b_plus_studio.dtos.UserResponse;
import com.api.b_plus_studio.entities.User;

import java.util.List;


public interface UserService {
    User findByEmail(String email);

    User createUser(CreateUserRequest request);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id) throws UserException;

    UserResponse updateUser(Long id, UserRequest request);

    void deleteUser(Long id);
}

