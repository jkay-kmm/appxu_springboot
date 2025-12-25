package com.anhtrung.app_xu.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private Long id;
    private String name;
    private String address;
    private Double lat;
    private Double lng;
    private String openTime;
    private Boolean isActive;
}