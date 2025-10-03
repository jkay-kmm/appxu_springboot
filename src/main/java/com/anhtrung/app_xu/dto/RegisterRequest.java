package com.anhtrung.app_xu.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter @Setter
public class RegisterRequest {
    @Email @NotBlank private String email;
    @Size(min = 6, max = 72) @NotBlank private String password;
    @NotBlank private String confirmPassword;
    @NotBlank private String fullName;
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Số điện thoại phải có 10-11 chữ số") 
    @NotBlank private String phoneNumber;
}
