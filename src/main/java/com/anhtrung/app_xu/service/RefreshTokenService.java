package com.anhtrung.app_xu.service;

import com.anhtrung.app_xu.domain.RefreshToken;
import com.anhtrung.app_xu.domain.User;
import com.anhtrung.app_xu.repo.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.UUID;


@Service @RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository repo;
    @Value("${app.jwt.refresh-token-ttl-days}") private long ttlDays;


    public RefreshToken issue(User user) {
        var rt = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiry(Instant.now().plusSeconds(ttlDays * 24 * 3600))
                .revoked(false)
                .build();
        return repo.save(rt);
    }


    public RefreshToken verify(String token) {
        var rt = repo.findByToken(token).orElseThrow(() -> new IllegalArgumentException("Invalid token"));
        if (rt.isRevoked() || rt.getExpiry().isBefore(Instant.now()))
            throw new IllegalStateException("Token expired or revoked");
        return rt;
    }


    public void revoke(String token) { repo.findByToken(token).ifPresent(rt -> { rt.setRevoked(true); repo.save(rt); }); }

    public void revokeAllForUser(Long userId) {
        var tokens = repo.findAllByUser_Id(userId);
        for (var rt : tokens) {
            if (!rt.isRevoked()) {
                rt.setRevoked(true);
                repo.save(rt);
            }
        }
    }
}
