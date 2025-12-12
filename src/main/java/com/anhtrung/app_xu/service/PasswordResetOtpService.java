package com.anhtrung.app_xu.service;

import com.anhtrung.app_xu.domain.PasswordResetOtp;
import com.anhtrung.app_xu.repo.PasswordResetOtpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PasswordResetOtpService {
    private final PasswordResetOtpRepository otpRepository;
    private final SecureRandom random = new SecureRandom();

    @Transactional
    public String generateOtp(String email) {
        // Xóa OTP cũ chưa sử dụng của email này
        otpRepository.deleteByEmailAndUsedFalse(email);
        
        // Tạo OTP 6 số
        String otp = String.format("%06d", random.nextInt(1000000));
        
        // Lưu OTP mới (hết hạn sau 5 phút)
        PasswordResetOtp otpEntity = PasswordResetOtp.builder()
                .email(email)
                .otp(otp)
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .createdAt(LocalDateTime.now())
                .used(false)
                .build();
        
        otpRepository.save(otpEntity);
        return otp;
    }

    @Transactional
    public boolean verifyOtp(String email, String otp) {
        var otpEntity = otpRepository.findByEmailAndOtpAndUsedFalseAndExpiresAtAfter(
                email, otp, LocalDateTime.now());
        
        if (otpEntity.isPresent()) {
            // Đánh dấu OTP đã sử dụng
            var entity = otpEntity.get();
            entity.setUsed(true);
            otpRepository.save(entity);
            return true;
        }
        return false;
    }

    @Transactional
    public void cleanupExpiredOtps() {
        otpRepository.deleteByExpiresAtBefore(LocalDateTime.now());
    }
}