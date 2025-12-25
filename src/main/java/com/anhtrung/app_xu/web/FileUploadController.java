package com.anhtrung.app_xu.web;

import com.anhtrung.app_xu.service.CloudinaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class FileUploadController {
    private final CloudinaryService cloudinaryService;

    @PostMapping("/image")
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Validate file
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "code", 400,
                        "message", "File không được để trống"
                ));
            }

            // Check file type
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.badRequest().body(Map.of(
                        "code", 400,
                        "message", "File phải là ảnh (jpg, png, gif, etc.)"
                ));
            }

            // Check file size (max 5MB)
            if (file.getSize() > 5 * 1024 * 1024) {
                return ResponseEntity.badRequest().body(Map.of(
                        "code", 400,
                        "message", "File không được vượt quá 5MB"
                ));
            }

            // Upload to Cloudinary
            String imageUrl = cloudinaryService.uploadImage(file);

            return ResponseEntity.ok(Map.of(
                    "code", 200,
                    "message", "Upload ảnh thành công",
                    "data", Map.of("url", imageUrl)
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "code", 500,
                    "message", "Upload ảnh thất bại: " + e.getMessage()
            ));
        }
    }

    @PostMapping("/image-resize")
    public ResponseEntity<Map<String, Object>> uploadImageWithResize(
            @RequestParam("file") MultipartFile file,
            @RequestParam(defaultValue = "400") int width,
            @RequestParam(defaultValue = "400") int height) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "code", 400,
                        "message", "File không được để trống"
                ));
            }

            String imageUrl = cloudinaryService.uploadImageWithTransformation(file, width, height);

            return ResponseEntity.ok(Map.of(
                    "code", 200,
                    "message", "Upload và resize ảnh thành công",
                    "data", Map.of("url", imageUrl)
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "code", 500,
                    "message", "Upload ảnh thất bại: " + e.getMessage()
            ));
        }
    }
}