package com.anhtrung.app_xu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateWasteRequestDto {
    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String phoneNumber;

    @NotBlank(message = "Tên liên hệ không được để trống")
    private String contactName;

    @NotNull(message = "Thời gian hẹn không được để trống")
    private LocalDateTime scheduledDate;

    private String notes;

    @NotEmpty(message = "Phải có ít nhất 1 loại rác")
    private List<WasteItemDto> items;

    // Đã xóa trường images

    @Data
    public static class WasteItemDto {
        @NotNull(message = "Category ID không được để trống")
        private Long categoryId;

        private Integer quantity = 1;
    }
}