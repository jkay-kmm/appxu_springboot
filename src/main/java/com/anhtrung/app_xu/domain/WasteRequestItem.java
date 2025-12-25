package com.anhtrung.app_xu.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "waste_request_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WasteRequestItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private WasteRequest wasteRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private Double estimatedWeight; // Cân nặng ước tính (kg)

    @Column(nullable = false)
    private Integer quantity = 1; // Số lượng

    private Double actualWeight; // Cân nặng thực tế (sau khi thu gom)

    private Double totalPrice; // Tổng tiền = actualWeight * category.price
}