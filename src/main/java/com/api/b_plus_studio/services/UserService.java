package com.api.b_plus_studio.services;

import com.api.b_plus_studio.Exceptions.UserException;
import com.api.b_plus_studio.dtos.CreateUserRequest;
import com.api.b_plus_studio.dtos.UserRequest;
import com.api.b_plus_studio.dtos.UserResponse;
import com.api.b_plus_studio.entities.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface UserService {
    User findByEmail(String email);

    User createUser(CreateUserRequest request);

    PagedModel<EntityModel<UserResponse>> getAllUsers(int page, int size, String sortBy);

    UserResponse getUserById(UUID id) throws UserException;

    UserResponse updateUser(UUID id, UserRequest request);

    void deleteUser(UUID id);
}

