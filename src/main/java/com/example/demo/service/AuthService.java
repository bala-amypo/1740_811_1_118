package com.example.demo.service;

import com.example.demo.model.AppUser;

import java.util.Optional;

public interface AuthService {
    AppUser registerUser(AppUser user);
    Optional<AppUser> findByUsername(String username);
    boolean existsByEmail(String email);
}

