package com.anhtrung.app_xu.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "users", indexes = {@Index(columnList = "email", unique = true)})
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String fullName;

    private String phoneNumber;

    @Builder.Default
    @Column(nullable = false)
    private Double coin = 0.0; // Số coin của user, mặc định 0

    @Builder.Default
    private boolean enabled = true;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Builder.Default
    private Set<Role> roles = new HashSet<>(Set.of(Role.USER)); // ✅ luôn non-null & mutable

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    // (phòng hờ trường hợp tạo entity không qua builder)
    @PrePersist
    private void prePersist() {
        if (roles == null || roles.isEmpty()) {
            roles = new HashSet<>(Set.of(Role.USER));
        }
    }
}
