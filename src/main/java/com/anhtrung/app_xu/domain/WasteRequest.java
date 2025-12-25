package com.anhtrung.app_xu.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "waste_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WasteRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Thông tin địa chỉ
    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String contactName;

    // Thông tin lịch hẹn
    @Column(nullable = false)
    private LocalDateTime scheduledDate;

    // Ghi chú thêm
    @Column(columnDefinition = "TEXT")
    private String notes;

    // Ảnh thực tế rác
    @ElementCollection
    @CollectionTable(name = "waste_request_images", joinColumns = @JoinColumn(name = "request_id"))
    @Column(name = "image_url")
    private List<String> images;

    // Trạng thái
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status = RequestStatus.PENDING;

    // Tổng tiền ước tính
    private Double estimatedTotal = 0.0;

    // Thời gian tạo
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}