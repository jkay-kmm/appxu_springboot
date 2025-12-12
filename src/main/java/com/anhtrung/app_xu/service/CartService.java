package com.anhtrung.app_xu.service;

import com.anhtrung.app_xu.domain.CartItem;
import com.anhtrung.app_xu.domain.User;
import com.anhtrung.app_xu.dto.CartItemDto;
import com.anhtrung.app_xu.dto.CategoryDto;
import com.anhtrung.app_xu.repo.CartItemRepository;
import com.anhtrung.app_xu.repo.CategoryRepository;
import com.anhtrung.app_xu.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public CartItemDto addToCart(String userEmail, Long categoryId, Double weight, Integer quantity) {
        System.out.println("Finding user: " + userEmail);
        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        System.out.println("User found: " + user.getId());
        
        System.out.println("Finding category: " + categoryId);
        System.out.println("Total categories in DB: " + categoryRepository.count());
        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category không tồn tại"));
        System.out.println("Category found: " + category.getNotes());

        // Kiểm tra xem đã có item này trong cart chưa
        var existingItem = cartItemRepository.findByUserAndCategoryId(user, categoryId);
        
        CartItem cartItem;
        if (existingItem.isPresent()) {
            // Cập nhật quantity và weight
            cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setWeight(cartItem.getWeight() + weight);
        } else {
            // Tạo mới
            cartItem = CartItem.builder()
                    .user(user)
                    .category(category)
                    .quantity(quantity)
                    .weight(weight)
                    .build();
        }
        
        cartItem = cartItemRepository.save(cartItem);
        return toDto(cartItem);
    }

    public List<CartItemDto> getCartItems(String userEmail) {
        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        
        return cartItemRepository.findByUserOrderByAddedAtDesc(user).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeFromCart(String userEmail, Long cartItemId) {
        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        
        var cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item không tồn tại"));
        
        if (!cartItem.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Không có quyền xóa item này");
        }
        
        cartItemRepository.delete(cartItem);
    }

    @Transactional
    public void clearCart(String userEmail) {
        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        
        cartItemRepository.deleteByUser(user);
    }

    public long getCartItemCount(String userEmail) {
        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        
        return cartItemRepository.countByUser(user);
    }

    private CartItemDto toDto(CartItem cartItem) {
        var categoryDto = CategoryDto.builder()
                .id(cartItem.getCategory().getId())
                .type(cartItem.getCategory().getType())
                .description(cartItem.getCategory().getDescription())
                .notes(cartItem.getCategory().getNotes())
                .price(cartItem.getCategory().getPrice())
                .image(cartItem.getCategory().getImage())
                .build();

        return CartItemDto.builder()
                .id(cartItem.getId())
                .category(categoryDto)
                .quantity(cartItem.getQuantity())
                .weight(cartItem.getWeight())
                .totalPrice(cartItem.getWeight() * cartItem.getCategory().getPrice())
                .addedAt(cartItem.getAddedAt())
                .build();
    }
}