package com.anhtrung.app_xu.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter @Setter
public class ForgotPasswordRequest {
    @Email @NotBlank private String email;
}