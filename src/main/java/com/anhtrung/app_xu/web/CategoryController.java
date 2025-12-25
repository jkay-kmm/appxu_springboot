package com.anhtrung.app_xu.web;

import com.anhtrung.app_xu.domain.WasteType;
import com.anhtrung.app_xu.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getCategories(
            @RequestParam(required = false) WasteType type
    ) {
        if (type != null) {
            var categories = categoryService.getCategoriesByType(type);
            return ResponseEntity.ok(Map.of(
                    "code", 200,
                    "message", "Lấy danh sách loại rác theo " + type + " thành công",
                    "data", categories
            ));
        }
        
        var categories = categoryService.getAllCategories();
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "message", "Lấy danh sách loại rác thành công",
                "data", categories
        ));
    }
}
