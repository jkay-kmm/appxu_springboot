package com.anhtrung.app_xu.repo;

import com.anhtrung.app_xu.domain.RequestStatus;
import com.anhtrung.app_xu.domain.User;
import com.anhtrung.app_xu.domain.WasteRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WasteRequestRepository extends JpaRepository<WasteRequest, Long> {
    List<WasteRequest> findByUserOrderByCreatedAtDesc(User user);
    
    List<WasteRequest> findByUserAndStatusOrderByCreatedAtDesc(User user, RequestStatus status);
    
    List<WasteRequest> findByStatusOrderByCreatedAtAsc(RequestStatus status);
    
    long countByUserAndStatus(User user, RequestStatus status);
}