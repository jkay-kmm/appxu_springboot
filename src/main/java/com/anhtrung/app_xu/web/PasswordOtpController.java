package com.anhtrung.app_xu.web;

import com.anhtrung.app_xu.dto.*;
import com.anhtrung.app_xu.service.MailService;
import com.anhtrung.app_xu.service.PasswordResetOtpService;
import com.anhtrung.app_xu.repo.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password-otp")
@RequiredArgsConstructor
public class PasswordOtpController {
    private final PasswordResetOtpService otpService;
    private final MailService mailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/send-otp")
    public ResponseEntity<SuccessResponse> sendOtp(@Valid @RequestBody ForgotPasswordOtpRequest request) {
        try {
            // Kiểm tra email có tồn tại không
            var user = userRepository.findByEmail(request.getEmail());
            if (user.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(SuccessResponse.success("Email không tồn tại trong hệ thống"));
            }

            // Tạo OTP và gửi email
            String otp = otpService.generateOtp(request.getEmail());
            System.out.println("Generated OTP: " + otp + " for email: " + request.getEmail());
            
            mailService.sendOtpMail(request.getEmail(), otp);
            System.out.println("Email sent successfully");

            return ResponseEntity.ok(SuccessResponse.success("Đã gửi mã OTP đến email của bạn"));
        } catch (Exception e) {
            System.err.println("Error in send-otp: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(SuccessResponse.success("Có lỗi xảy ra khi gửi OTP: " + e.getMessage()));
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<SuccessResponse> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
        boolean isValid = otpService.verifyOtp(request.getEmail(), request.getOtp());
        
        if (isValid) {
            return ResponseEntity.ok(SuccessResponse.success("Xác thực OTP thành công"));
        } else {
            return ResponseEntity.badRequest()
                    .body(SuccessResponse.success("OTP không đúng hoặc đã hết hạn"));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<SuccessResponse> resetPassword(@Valid @RequestBody ResetPasswordWithOtpRequest request) {
        // Kiểm tra password và confirmPassword
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest()
                    .body(SuccessResponse.success("Mật khẩu và xác nhận mật khẩu không khớp"));
        }

        // Xác thực OTP một lần nữa
        boolean isValidOtp = otpService.verifyOtp(request.getEmail(), request.getOtp());
        if (!isValidOtp) {
            return ResponseEntity.badRequest()
                    .body(SuccessResponse.success("OTP không đúng hoặc đã hết hạn"));
        }

        // Cập nhật mật khẩu
        var user = userRepository.findByEmail(request.getEmail());
        if (user.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(SuccessResponse.success("Email không tồn tại trong hệ thống"));
        }

        var userEntity = user.get();
        userEntity.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(userEntity);

        return ResponseEntity.ok(SuccessResponse.success("Đặt lại mật khẩu thành công"));
    }
}