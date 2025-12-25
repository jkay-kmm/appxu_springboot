package com.anhtrung.app_xu.service;

import com.anhtrung.app_xu.domain.*;
import com.anhtrung.app_xu.dto.CreateWasteRequestDto;
import com.anhtrung.app_xu.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WasteRequestService {
    private final WasteRequestRepository wasteRequestRepository;
    private final WasteRequestItemRepository wasteRequestItemRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public WasteRequest createRequest(String userEmail, CreateWasteRequestDto dto) {
        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        // Tạo waste request
        var wasteRequest = WasteRequest.builder()
                .user(user)
                .address(dto.getAddress())
                .phoneNumber(dto.getPhoneNumber())
                .contactName(dto.getContactName())
                .scheduledDate(dto.getScheduledDate())
                .notes(dto.getNotes())
                .images(dto.getImages())
                .status(RequestStatus.PENDING)
                .build();

        wasteRequest = wasteRequestRepository.save(wasteRequest);

        // Tạo waste request items và tính tổng tiền ước tính
        double estimatedTotal = 0.0;
        for (var itemDto : dto.getItems()) {
            var category = categoryRepository.findById(itemDto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category không tồn tại"));

            var item = WasteRequestItem.builder()
                    .wasteRequest(wasteRequest)
                    .category(category)
                    .estimatedWeight(itemDto.getEstimatedWeight())
                    .quantity(itemDto.getQuantity())
                    .build();

            wasteRequestItemRepository.save(item);
            estimatedTotal += itemDto.getEstimatedWeight() * category.getPrice();
        }

        // Cập nhật tổng tiền ước tính
        wasteRequest.setEstimatedTotal(estimatedTotal);
        return wasteRequestRepository.save(wasteRequest);
    }

    public List<WasteRequest> getUserRequests(String userEmail) {
        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        return wasteRequestRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public List<WasteRequest> getUserRequestsByStatus(String userEmail, RequestStatus status) {
        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        return wasteRequestRepository.findByUserAndStatusOrderByCreatedAtDesc(user, status);
    }

    public WasteRequest getRequestById(Long requestId, String userEmail) {
        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        
        var request = wasteRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request không tồn tại"));
        
        if (!request.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Không có quyền truy cập request này");
        }
        
        return request;
    }

    @Transactional
    public void cancelRequest(Long requestId, String userEmail) {
        var request = getRequestById(requestId, userEmail);
        
        if (request.getStatus() != RequestStatus.PENDING) {
            throw new RuntimeException("Chỉ có thể hủy request đang chờ xử lý");
        }
        
        request.setStatus(RequestStatus.CANCELLED);
        wasteRequestRepository.save(request);
    }
}