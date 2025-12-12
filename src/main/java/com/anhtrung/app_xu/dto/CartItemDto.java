package com.anhtrung.app_xu.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long id;
    private CategoryDto category;
    private Integer quantity;
    private Double weight;
    private Double totalPrice; // weight * category.price
    private LocalDateTime addedAt;
}