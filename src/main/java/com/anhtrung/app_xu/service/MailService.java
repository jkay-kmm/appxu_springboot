package com.anhtrung.app_xu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service @RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    @Value("${app.frontend-reset-url}") private String resetBase;
    @Value("${app.frontend-verify-url:http://localhost:3000/verify-email?token=}") private String verifyBase;


    public void sendResetMail(String to, String token) {
        var msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject("Password reset");
        msg.setText("Reset your password: " + resetBase + token);
        mailSender.send(msg);
    }

    public void sendVerifyMail(String to, String token) {
        var msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject("Verify your email");
        msg.setText("Verify your email: " + verifyBase + token);
        mailSender.send(msg);
    }

    public void sendOtpMail(String to, String otp) {
        try {
            var msg = new SimpleMailMessage();
            msg.setTo(to);
            msg.setSubject("Mã OTP đặt lại mật khẩu ");
            msg.setText("Mã OTP của bạn là: " + otp + "\n\n" +
                    "Mã này có hiệu lực trong 5 phút.\n" +
                    "Vui lòng không chia sẻ mã này với ai khác.\n\n" +
                    "Trân trọng,\nGreenXu Team");
            mailSender.send(msg);
        } catch (Exception e) {
            System.out.println("Chưa có mã otp");
           
        }
    }
}