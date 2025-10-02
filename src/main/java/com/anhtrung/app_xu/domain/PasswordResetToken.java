package com.anhtrung.app_xu.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class PasswordResetToken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    @Column(nullable = false, unique = true, length = 160)
    private String token;


    @Column(nullable = false)
    private Instant expiry;


    private boolean used;
}