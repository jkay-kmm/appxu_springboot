package com.anhtrung.app_xu.web;

import com.anhtrung.app_xu.domain.RequestStatus;
import com.anhtrung.app_xu.dto.CreateWasteRequestDto;
import com.anhtrung.app_xu.service.WasteRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/waste-requests")
@RequiredArgsConstructor
public class WasteRequestController {
    private final WasteRequestService wasteRequestService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createRequest(@Valid @RequestBody CreateWasteRequestDto dto) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = auth.getName();

            var request = wasteRequestService.createRequest(userEmail, dto);

            return ResponseEntity.ok(Map.of(
                    "code", 200,
                    "message", "Tạo yêu cầu thu gom thành công",
                    "data", Map.of(
                            "requestId", request.getId(),
                            "status", request.getStatus(),
                            "estimatedTotal", request.getEstimatedTotal(),
                            "scheduledDate", request.getScheduledDate()
                    )
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "code", 500,
                    "message", "Lỗi khi tạo yêu cầu: " + e.getMessage()
            ));
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getUserRequests(
            @RequestParam(required = false) RequestStatus status) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = auth.getName();

            var requests = status != null 
                    ? wasteRequestService.getUserRequestsByStatus(userEmail, status)
                    : wasteRequestService.getUserRequests(userEmail);

            return ResponseEntity.ok(Map.of(
                    "code", 200,
                    "message", "Lấy danh sách yêu cầu thành công",
                    "data", requests
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "code", 500,
                    "message", "Lỗi khi lấy danh sách: " + e.getMessage()
            ));
        }
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Map<String, Object>> getRequestDetail(@PathVariable Long requestId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = auth.getName();

            var request = wasteRequestService.getRequestById(requestId, userEmail);

            return ResponseEntity.ok(Map.of(
                    "code", 200,
                    "message", "Lấy chi tiết yêu cầu thành công",
                    "data", request
            ));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of(
                    "code", 404,
                    "message", e.getMessage()
            ));
        }
    }

    @PutMapping("/{requestId}/cancel")
    public ResponseEntity<Map<String, Object>> cancelRequest(@PathVariable Long requestId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = auth.getName();

            wasteRequestService.cancelRequest(requestId, userEmail);

            return ResponseEntity.ok(Map.of(
                    "code", 200,
                    "message", "Hủy yêu cầu thành công"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of(
                    "code", 400,
                    "message", e.getMessage()
            ));
        }
    }
}