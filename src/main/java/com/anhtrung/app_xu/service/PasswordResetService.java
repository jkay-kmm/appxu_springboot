package com.anhtrung.app_xu.service;

import com.anhtrung.app_xu.domain.PasswordResetToken;
import com.anhtrung.app_xu.repo.PasswordResetTokenRepository;
import com.anhtrung.app_xu.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.UUID;


@Service @RequiredArgsConstructor
public class PasswordResetService {
    private final PasswordResetTokenRepository tokens;
    private final UserRepository users;


    public String createToken(String email) {
        var user = users.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Email not found"));
        var token = PasswordResetToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiry(Instant.now().plusSeconds(15 * 60)) // 15 minutes
                .used(false)
                .build();
        tokens.save(token);
        return token.getToken();
    }


    public Long consume(String token) {
        var t = tokens.findByToken(token).orElseThrow(() -> new IllegalArgumentException("Invalid token"));
        if (t.isUsed() || t.getExpiry().isBefore(Instant.now())) throw new IllegalStateException("Token expired or used");
        t.setUsed(true); tokens.save(t);
        return t.getUser().getId();
    }
}