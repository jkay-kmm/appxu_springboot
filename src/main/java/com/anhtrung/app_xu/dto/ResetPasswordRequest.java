package com.anhtrung.app_xu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter @Setter
public class ResetPasswordRequest {
    @NotBlank private String token;
    @Size(min = 6, max = 72) @NotBlank private String newPassword;
}
