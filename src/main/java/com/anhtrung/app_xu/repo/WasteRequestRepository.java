package com.anhtrung.app_xu.repo;

import com.anhtrung.app_xu.domain.RequestStatus;
import com.anhtrung.app_xu.domain.User;
import com.anhtrung.app_xu.domain.WasteRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WasteRequestRepository extends JpaRepository<WasteRequest, Long> {
    List<WasteRequest> findByUserOrderByCreatedAtDesc(User user);
    
    List<WasteRequest> findByUserAndStatusOrderByCreatedAtDesc(User user, RequestStatus status);
    
    List<WasteRequest> findByStatusOrderByCreatedAtAsc(RequestStatus status);
    
    long countByUserAndStatus(User user, RequestStatus status);
    
    @Query("SELECT wr FROM WasteRequest wr LEFT JOIN FETCH wr.items i LEFT JOIN FETCH i.category WHERE wr.id = :id")
    Optional<WasteRequest> findByIdWithItems(@Param("id") Long id);
}