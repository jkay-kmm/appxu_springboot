package com.anhtrung.app_xu.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter @Setter
public class RegisterRequest {
    @Email @NotBlank private String email;
    @Size(min = 6, max = 72) @NotBlank private String password;
    @NotBlank private String fullName;
}
