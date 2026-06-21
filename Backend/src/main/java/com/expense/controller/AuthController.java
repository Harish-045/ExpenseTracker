package com.expense.controller;

import com.expense.dto.AuthResponse;
import com.expense.dto.LoginRequest;
import com.expense.dto.RegisterRequest;
import com.expense.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }
    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody LoginRequest request) {

        return authService.login(request);
    }
}