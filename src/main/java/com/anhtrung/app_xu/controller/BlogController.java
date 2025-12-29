package com.anhtrung.app_xu.controller;

import com.anhtrung.app_xu.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllBlogs() {
        var blogs = blogService.getAllBlogs();
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "message", "Lấy danh sách blog thành công",
                "data", blogs
        ));
    }
}
