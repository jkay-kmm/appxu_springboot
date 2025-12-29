package com.anhtrung.app_xu.controller;
import com.anhtrung.app_xu.dto.ForgotPasswordRequest;
import com.anhtrung.app_xu.dto.ResetPasswordRequest;
import com.anhtrung.app_xu.dto.SuccessResponse;
import com.anhtrung.app_xu.service.MailService;
import com.anhtrung.app_xu.service.PasswordResetService;
import com.anhtrung.app_xu.service.UserService;
import com.anhtrung.app_xu.repo.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController @RequestMapping("/api/password") @RequiredArgsConstructor
public class PasswordController {
    private final PasswordResetService resets;
    private final MailService mail;
    private final UserService users;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;


    @PostMapping("/forgot")
    public ResponseEntity<SuccessResponse> forgot(@Valid @RequestBody ForgotPasswordRequest r){
        String token = resets.createToken(r.getEmail());
        mail.sendResetMail(r.getEmail(), token);
        return ResponseEntity.ok(SuccessResponse.success("Đã gửi email đặt lại mật khẩu"));
    }


    @PostMapping("/reset")
    public ResponseEntity<SuccessResponse> reset(@Valid @RequestBody ResetPasswordRequest r){
        Long userId = resets.consume(r.getToken());
        var u = users.getById(userId);
        u.setPassword(encoder.encode(r.getNewPassword()));
        userRepository.save(u);
        return ResponseEntity.ok(SuccessResponse.success("Đặt lại mật khẩu thành công"));
    }
}