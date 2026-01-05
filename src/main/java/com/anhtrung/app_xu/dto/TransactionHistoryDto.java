package com.anhtrung.app_xu.dto;

import com.anhtrung.app_xu.domain.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoryDto {
    private Long id;
    private Double price;
    private RequestStatus status;
    private LocalDateTime createdAt;
}