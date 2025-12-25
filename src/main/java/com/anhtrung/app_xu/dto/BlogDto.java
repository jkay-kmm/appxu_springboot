package com.anhtrung.app_xu.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime time;
    private String image;
}