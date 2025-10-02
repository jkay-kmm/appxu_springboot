package com.anhtrung.app_xu.web;

import com.anhtrung.app_xu.dto.*;
import com.anhtrung.app_xu.service.AuthService;
import com.anhtrung.app_xu.service.RefreshTokenService;
import com.anhtrung.app_xu.service.EmailVerificationService;
import com.anhtrung.app_xu.service.MailService;
import com.anhtrung.app_xu.repo.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import com.anhtrung.app_xu.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.anhtrung.app_xu.dto.ChangePasswordRequest;


@RestController @RequestMapping("/api/auth") @RequiredArgsConstructor
public class AuthController {
    private final AuthService auth;
    private final RefreshTokenService refreshTokens;
    private final UserRepository users;
    private final EmailVerificationService emailVerifications;
    private final MailService mail;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    @Value("${app.jwt.refresh-token-ttl-days:7}") private long refreshTtlDays;
    @Value("${app.cookie.secure:false}") private boolean cookieSecure;


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest r){
        var res = auth.register(r.getEmail(), r.getPassword(), r.getFullName());
        var cookie = ResponseCookie.from("refreshToken", res.getRefreshToken())
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/api/auth")
                .maxAge(refreshTtlDays * 24 * 3600)
                .sameSite("Lax")
                .build();
        var user = users.findByEmail(r.getEmail()).orElseThrow();
        var userDto = UserDto.builder().id(user.getId()).email(user.getEmail()).fullName(user.getFullName()).roles(user.getRoles()).enabled(user.isEnabled()).build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(AuthResponse.builder().accessToken(res.getAccessToken()).tokenType("Bearer").expiresIn(jwt.getAccessTtlMillis()/1000).user(userDto).build());
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest r){
        var res = auth.login(r.getEmail(), r.getPassword());
        var cookie = ResponseCookie.from("refreshToken", res.getRefreshToken())
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/api/auth")
                .maxAge(refreshTtlDays * 24 * 3600)
                .sameSite("Lax")
                .build();
        var user = users.findByEmail(r.getEmail()).orElseThrow();
        var userDto = UserDto.builder().id(user.getId()).email(user.getEmail()).fullName(user.getFullName()).roles(user.getRoles()).enabled(user.isEnabled()).build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(AuthResponse.builder().accessToken(res.getAccessToken()).tokenType("Bearer").expiresIn(jwt.getAccessTtlMillis()/1000).user(userDto).build());
    }


    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@CookieValue("refreshToken") String refreshToken){
        var res = auth.refresh(refreshToken);
        // rotate refresh token
        var cookie = ResponseCookie.from("refreshToken", res.getRefreshToken())
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/api/auth")
                .maxAge(refreshTtlDays * 24 * 3600)
                .sameSite("Lax")
                .build();
        // whoami data is not required here, keep minimal
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(AuthResponse.builder().accessToken(res.getAccessToken()).tokenType("Bearer").expiresIn(jwt.getAccessTtlMillis()/1000).build());
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue("refreshToken") String refreshToken){
        auth.logout(refreshToken);
        var clear = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/api/auth")
                .maxAge(0)
                .sameSite("Lax")
                .build();
        return ResponseEntity.noContent().header(HttpHeaders.SET_COOKIE, clear.toString()).build();
    }

    @PostMapping("/logout-all")
    public ResponseEntity<Void> logoutAll(){
        Authentication authn = SecurityContextHolder.getContext().getAuthentication();
        var email = authn.getName();
        var user = users.findByEmail(email).orElseThrow();
        refreshTokens.revokeAllForUser(user.getId());
        var clear = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/api/auth")
                .maxAge(0)
                .sameSite("Lax")
                .build();
        return ResponseEntity.noContent().header(HttpHeaders.SET_COOKIE, clear.toString()).build();
    }

    @GetMapping("/whoami")
    public ResponseEntity<MapResponse> whoami(){
        Authentication authn = SecurityContextHolder.getContext().getAuthentication();
        var email = authn.getName();
        var user = users.findByEmail(email).orElseThrow();
        return ResponseEntity.ok(new MapResponse(user.getId(), user.getEmail(), user.getFullName(), user.getRoles(), user.isEnabled()));
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest r){
        Authentication authn = SecurityContextHolder.getContext().getAuthentication();
        var email = authn.getName();
        var user = users.findByEmail(email).orElseThrow();
        if (!encoder.matches(r.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.status(400).build();
        }
        user.setPassword(encoder.encode(r.getNewPassword()));
        users.save(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<Void> resendVerification(@RequestParam String email){
        var user = users.findByEmail(email).orElseThrow();
        var token = emailVerifications.issue(user);
        mail.sendVerifyMail(user.getEmail(), token.getToken());
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/verify-email")
    public ResponseEntity<Void> verifyEmail(@RequestParam String token){
        var user = emailVerifications.verify(token);
        return ResponseEntity.noContent().build();
    }
}

class MapResponse {
    public Long id; public String email; public String fullName; public java.util.Set<com.anhtrung.app_xu.domain.Role> roles; public boolean enabled;
    public MapResponse(Long id, String email, String fullName, java.util.Set<com.anhtrung.app_xu.domain.Role> roles, boolean enabled){ this.id=id; this.email=email; this.fullName=fullName; this.roles=roles; this.enabled=enabled; }
}
