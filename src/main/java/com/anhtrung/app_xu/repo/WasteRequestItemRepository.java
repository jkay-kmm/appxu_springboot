package com.anhtrung.app_xu.repo;

import com.anhtrung.app_xu.domain.WasteRequest;
import com.anhtrung.app_xu.domain.WasteRequestItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WasteRequestItemRepository extends JpaRepository<WasteRequestItem, Long> {
    List<WasteRequestItem> findByWasteRequest(WasteRequest wasteRequest);
    
    @Query("SELECT wri FROM WasteRequestItem wri LEFT JOIN FETCH wri.category WHERE wri.wasteRequest = :wasteRequest")
    List<WasteRequestItem> findByWasteRequestWithCategory(@Param("wasteRequest") WasteRequest wasteRequest);
}