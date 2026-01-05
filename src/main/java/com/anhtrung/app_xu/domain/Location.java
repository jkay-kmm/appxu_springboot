package com.anhtrung.app_xu.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // Mã địa điểm như MK001, HN002, etc.

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Double lat; // Latitude

    @Column(nullable = false)
    private Double lng; // Longitude

    @Column(nullable = false)
    private String openTime;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;
}