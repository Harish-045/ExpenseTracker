package com.expense.service;

import com.expense.dto.AuthResponse;
import com.expense.dto.ForgotPasswordRequest;
import com.expense.dto.LoginRequest;
import com.expense.dto.RegisterRequest;
import com.expense.dto.ResetPasswordRequest;
import com.expense.entity.User;
import com.expense.repository.UserRepository;
import com.expense.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;

    // =========================
    // EXISTING REGISTER
    // =========================
    public String register(RegisterRequest request) {

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        return "User Registered Successfully";
    }

    // =========================
    // EXISTING LOGIN
    // =========================
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);
    }

    // =========================
    // FORGOT PASSWORD
    // =========================
    public String forgotPassword(ForgotPasswordRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Email not registered"));

        // Generate secure random token
        String token = UUID.randomUUID().toString();

        // Token valid for 15 minutes
        LocalDateTime expiry =
                LocalDateTime.now().plusMinutes(15);

        user.setResetToken(token);
        user.setResetTokenExpiry(expiry);

        userRepository.save(user);

        // Reset link
        String resetLink =
                "http://13.234.67.184/reset-password?token=" + token;

        emailService.sendPasswordResetEmail(
                user.getEmail(),
                resetLink
        );

        return "Password reset link sent to your email";
    }

    // =========================
    // RESET PASSWORD
    // =========================
    public String resetPassword(ResetPasswordRequest request) {

        User user = userRepository
                .findByResetToken(request.getToken())
                .orElseThrow(() ->
                        new RuntimeException("Invalid or expired reset token"));

        // Check token expiry
        if (user.getResetTokenExpiry() == null ||
                user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {

            throw new RuntimeException("Reset token has expired");
        }

        // Set new password
        user.setPassword(
                passwordEncoder.encode(request.getNewPassword())
        );

        // Remove token so it cannot be reused
        user.setResetToken(null);
        user.setResetTokenExpiry(null);

        userRepository.save(user);

        return "Password reset successfully";
    }
}
