package com.expense.controller;

import com.expense.dto.ChangePasswordRequest;
import com.expense.dto.ProfileResponse;
import com.expense.dto.UpdateProfileRequest;
import com.expense.service.ProfileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ProfileResponse getProfile() {
        return profileService.getProfile();
    }

    @PutMapping
    public ProfileResponse updateProfile(
            @RequestBody UpdateProfileRequest request) {

        return profileService.updateProfile(request);
    }

    @PutMapping("/password")
    public String changePassword(
            @RequestBody ChangePasswordRequest request) {

        profileService.changePassword(request);

        return "Password Updated Successfully";
    }
    @PostMapping("/image")
    public String uploadProfileImage(
            @RequestParam("file") MultipartFile file) throws IOException {

        return profileService.uploadProfileImage(file);

    }

}