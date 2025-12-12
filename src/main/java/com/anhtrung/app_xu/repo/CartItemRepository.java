package com.anhtrung.app_xu.repo;

import com.anhtrung.app_xu.domain.CartItem;
import com.anhtrung.app_xu.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUserOrderByAddedAtDesc(User user);
    
    Optional<CartItem> findByUserAndCategoryId(User user, Long categoryId);
    
    void deleteByUser(User user);
    
    long countByUser(User user);
}