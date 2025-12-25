package com.anhtrung.app_xu.repo;

import com.anhtrung.app_xu.domain.WasteRequest;
import com.anhtrung.app_xu.domain.WasteRequestItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WasteRequestItemRepository extends JpaRepository<WasteRequestItem, Long> {
    List<WasteRequestItem> findByWasteRequest(WasteRequest wasteRequest);
}