package com.anhtrung.app_xu.service;

import com.anhtrung.app_xu.domain.Role;
import com.anhtrung.app_xu.domain.User;
import com.anhtrung.app_xu.dto.AuthResponse;
import com.anhtrung.app_xu.exception.EmailAlreadyInUseException;
import com.anhtrung.app_xu.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authManager;
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;
    private final RefreshTokenService refreshTokens;

    public AuthResponse login(String email, String password) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        User user = users.findByEmail(email).orElseThrow();
        var access = jwt.generateAccessToken(user);
        var refresh = refreshTokens.issue(user).getToken();
        return AuthResponse.builder().accessToken(access).refreshToken(refresh).build();
    }

    public AuthResponse register(String email, String rawPassword, String fullName) {
        String normalizedEmail = email == null ? null : email.trim().toLowerCase();
        if (users.existsByEmail(normalizedEmail)) throw new EmailAlreadyInUseException("Email already in use");
        User user = User.builder()
                .email(normalizedEmail)
                .password(encoder.encode(rawPassword))
                .fullName(fullName)
                .enabled(true)
                .roles(new HashSet<>(Set.of(Role.USER))) // ✅ gán role mặc định
                .build();
        user = users.save(user);
        var access = jwt.generateAccessToken(user);
        var refresh = refreshTokens.issue(user).getToken();
        return AuthResponse.builder().accessToken(access).refreshToken(refresh).build();
    }

    public AuthResponse refresh(String refreshToken) {
        var rt = refreshTokens.verify(refreshToken);
        var access = jwt.generateAccessToken(rt.getUser());
        return AuthResponse.builder().accessToken(access).refreshToken(refreshToken).build();
    }

    public void logout(String refreshToken) {
        refreshTokens.revoke(refreshToken);
    }
}
