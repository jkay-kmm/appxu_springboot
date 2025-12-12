package com.anhtrung.app_xu.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddToCartRequest {
    @NotNull(message = "Category ID không được để trống")
    private Long categoryId;

    @NotNull(message = "Cân nặng không được để trống")
    @Positive(message = "Cân nặng phải lớn hơn 0")
    private Double weight; // Cân nặng thực tế (kg)

    private Integer quantity = 1; // Số lượng (mặc định 1)
}