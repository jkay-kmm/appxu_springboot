package com.anhtrung.app_xu.service;

import com.anhtrung.app_xu.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class JwtService {
    private final Key key;
    private final String issuer;
    private final long accessTtlMillis;


    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.issuer}") String issuer,
            @Value("${app.jwt.access-token-ttl-min}") long accessTtlMin
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.issuer = issuer;
        this.accessTtlMillis = accessTtlMin * 60_000;
    }


    public String generateAccessToken(User user) {
        Instant now = Instant.now();
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        if (user.getRoles() != null) {
            claims.put("roles", user.getRoles());
        }
        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(user.getId().toString())
                .addClaims(claims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(accessTtlMillis)))
                .signWith(key)
                .compact();
    }

    public long getAccessTtlMillis() { return accessTtlMillis; }



    public Long extractUserId(String token) {
        var claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
        return Long.valueOf(claims.getSubject());
    }
}
