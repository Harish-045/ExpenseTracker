package com.expense.controller;

import com.expense.dto.AuthResponse;
import com.expense.dto.ForgotPasswordRequest;
import com.expense.dto.LoginRequest;
import com.expense.dto.RegisterRequest;
import com.expense.dto.ResetPasswordRequest;
import com.expense.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // =========================
    // EXISTING REGISTER
    // =========================
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    // =========================
    // EXISTING LOGIN
    // =========================
    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody LoginRequest request) {

        return authService.login(request);
    }

    // =========================
    // FORGOT PASSWORD
    // =========================
    @PostMapping("/forgot-password")
    public String forgotPassword(
            @RequestBody ForgotPasswordRequest request) {

        return authService.forgotPassword(request);
    }

    // =========================
    // RESET PASSWORD
    // =========================
    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestBody ResetPasswordRequest request) {

        return authService.resetPassword(request);
    }
}
