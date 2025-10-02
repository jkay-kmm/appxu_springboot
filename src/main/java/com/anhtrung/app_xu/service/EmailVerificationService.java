package com.anhtrung.app_xu.service;

import com.anhtrung.app_xu.domain.EmailVerificationToken;
import com.anhtrung.app_xu.domain.User;
import com.anhtrung.app_xu.repo.EmailVerificationTokenRepository;
import com.anhtrung.app_xu.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service @RequiredArgsConstructor
public class EmailVerificationService {
    private final EmailVerificationTokenRepository repo;
    private final UserRepository users;
    @Value("${app.verify.ttl-hours:24}") private long ttlHours;

    public EmailVerificationToken issue(User user) {
        var t = EmailVerificationToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiry(Instant.now().plusSeconds(ttlHours * 3600))
                .used(false)
                .build();
        return repo.save(t);
    }

    public User verify(String token) {
        var t = repo.findByToken(token).orElseThrow(() -> new IllegalArgumentException("Invalid token"));
        if (t.isUsed() || t.getExpiry().isBefore(Instant.now())) throw new IllegalStateException("Token expired or used");
        t.setUsed(true);
        repo.save(t);
        var user = t.getUser();
        user.setEnabled(true);
        users.save(user);
        return user;
    }
}


