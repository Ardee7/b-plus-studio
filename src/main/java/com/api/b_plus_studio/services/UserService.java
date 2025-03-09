package com.api.b_plus_studio.services;

import com.api.b_plus_studio.entities.User;


public interface UserService {
    public User findByEmail(String email);
}

