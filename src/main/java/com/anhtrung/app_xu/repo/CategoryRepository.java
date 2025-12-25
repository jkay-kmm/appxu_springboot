package com.anhtrung.app_xu.repo;

import com.anhtrung.app_xu.domain.Category;
import com.anhtrung.app_xu.domain.WasteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByType(WasteType type);
}
