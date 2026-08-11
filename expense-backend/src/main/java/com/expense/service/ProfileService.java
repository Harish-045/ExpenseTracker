package com.expense.service;

import com.expense.dto.ChangePasswordRequest;
import com.expense.dto.ProfileResponse;
import com.expense.dto.UpdateProfileRequest;
import com.expense.entity.User;
import com.expense.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private User getCurrentUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    public ProfileResponse getProfile() {

        User user = getCurrentUser();

        return new ProfileResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getProfileImage()
        );
    }

    public ProfileResponse updateProfile(
            UpdateProfileRequest request) {

        User user = getCurrentUser();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        User updated = userRepository.save(user);

        return new ProfileResponse(
                updated.getId(),
                updated.getName(),
                updated.getEmail(),
                updated.getProfileImage()
        );
    }

    public void changePassword(
            ChangePasswordRequest request) {

        User user = getCurrentUser();

        if (!passwordEncoder.matches(
                request.getCurrentPassword(),
                user.getPassword())) {

            throw new RuntimeException(
                    "Current password is incorrect");
        }

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()));

        userRepository.save(user);
    }
    public String uploadProfileImage(MultipartFile file)
            throws IOException {

        User user = getCurrentUser();

        String uploadDir = "uploads/";

        File folder = new File(uploadDir);

        if (!folder.exists()) {

            folder.mkdirs();

        }

        String fileName =
                System.currentTimeMillis() + "_" + file.getOriginalFilename();

        Path path =
                Paths.get(uploadDir, fileName);

        Files.copy(
                file.getInputStream(),
                path,
                StandardCopyOption.REPLACE_EXISTING
        );

        user.setProfileImage(fileName);

        userRepository.save(user);

        return "Profile Image Uploaded";

    }
}