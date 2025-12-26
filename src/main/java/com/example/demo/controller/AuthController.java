package com.example.demo.controller;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.security.JwtTokenProvider;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public JwtResponse register(@RequestBody RegisterRequest request) {
        String token = jwtTokenProvider.generateToken(request.getUsername());
        return new JwtResponse(token);
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest request) {
        String token = jwtTokenProvider.generateToken(request.getUsername());
        return new JwtResponse(token);
    }
}
