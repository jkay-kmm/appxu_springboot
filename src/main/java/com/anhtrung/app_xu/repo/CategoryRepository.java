package com.anhtrung.app_xu.repo;

import com.anhtrung.app_xu.domain.Category;
import com.anhtrung.app_xu.domain.WasteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByType(WasteType type);
    
    // Tìm kiếm theo từ khóa trong notes và description
    @Query("SELECT c FROM Category c WHERE " +
           "LOWER(c.notes) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Category> findByKeyword(@Param("keyword") String keyword);
    
    // Tìm kiếm theo từ khóa và type
    @Query("SELECT c FROM Category c WHERE " +
           "(LOWER(c.notes) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "c.type = :type")
    List<Category> findByKeywordAndType(@Param("keyword") String keyword, @Param("type") WasteType type);
}
