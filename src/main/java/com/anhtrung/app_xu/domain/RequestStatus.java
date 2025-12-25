package com.anhtrung.app_xu.domain;

public enum RequestStatus {
    PENDING,     // Chờ xử lý
    CONFIRMED,   // Đã xác nhận
    IN_PROGRESS, // Đang thu gom
    COMPLETED,   // Hoàn thành
    CANCELLED    // Đã hủy
}