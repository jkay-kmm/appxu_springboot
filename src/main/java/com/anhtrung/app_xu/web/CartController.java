package com.anhtrung.app_xu.web;

import com.anhtrung.app_xu.dto.AddToCartRequest;
import com.anhtrung.app_xu.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addToCart(@Valid @RequestBody AddToCartRequest request) {
        try {
            System.out.println("=== DEBUG ADD TO CART ===");
            System.out.println("Request: " + request);
            
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = auth.getName();
            System.out.println("User email: " + userEmail);
            
            var cartItem = cartService.addToCart(userEmail, request.getCategoryId(), 
                                               request.getQuantity());
            
            System.out.println("Cart item created successfully");
            
            // Trả về đầy đủ thông tin category
            return ResponseEntity.ok(Map.of(
                    "code", 200,
                    "message", "Thêm vào thùng thành công",
                    "data", Map.of(
                            "id", cartItem.getCategory().getId(),
                            "image", cartItem.getCategory().getImage(),
                            "notes", cartItem.getCategory().getNotes(),
                            "price", cartItem.getCategory().getPrice(),
                            "description", cartItem.getCategory().getDescription(),
                            "type", cartItem.getCategory().getType(),
    
                            "quantity", cartItem.getQuantity(),
                            "totalPrice", cartItem.getTotalPrice(),
                            "addedAt", cartItem.getAddedAt()
                    )
            ));
        } catch (Exception e) {
            System.err.println("Error in addToCart: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "code", 500,
                    "message", "Lỗi khi thêm vào thùng: " + e.getMessage()
            ));
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getCartItems() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        
        var cartItems = cartService.getCartItems(userEmail);
        var totalItems = cartService.getCartItemCount(userEmail);
        var totalPrice = cartItems.stream()
                .mapToDouble(item -> item.getTotalPrice())
                .sum();
        
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "message", "Lấy danh sách thùng rác thành công",
                "data", Map.of(
                        "items", cartItems,
                        "totalItems", totalItems,
                        "totalPrice", totalPrice
                )
        ));
    }

    @GetMapping("/{cartItemId}")
    public ResponseEntity<Map<String, Object>> getCartItem(@PathVariable Long cartItemId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        
        try {
            var cartItem = cartService.getCartItem(userEmail, cartItemId);
            return ResponseEntity.ok(Map.of(
                    "code", 200,
                    "message", "Lấy thông tin cart item thành công",
                    "data", cartItem
            ));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of(
                    "code", 404,
                    "message", "Cart item không tồn tại hoặc không thuộc về bạn"
            ));
        }
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Map<String, Object>> removeFromCart(@PathVariable Long cartItemId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        
        cartService.removeFromCart(userEmail, cartItemId);
        
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "message", "Xóa khỏi thùng thành công"
        ));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Map<String, Object>> clearCart() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        
        cartService.clearCart(userEmail);
        
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "message", "Xóa tất cả khỏi thùng thành công"
        ));
    }
}