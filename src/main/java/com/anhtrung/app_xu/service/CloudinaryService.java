package com.anhtrung.app_xu.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) throws IOException {
        // Upload file to Cloudinary với transformation đơn giản
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), 
            ObjectUtils.asMap(
                "folder", "greenxu", // Tạo folder greenxu trên Cloudinary
                "resource_type", "image",
                "quality", "auto:good" // Tự động optimize chất lượng
            )
        );

        // Trả về URL của ảnh đã upload
        return (String) uploadResult.get("secure_url");
    }

    public String uploadImageWithTransformation(MultipartFile file, int width, int height) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), 
            ObjectUtils.asMap(
                "folder", "greenxu",
                "resource_type", "image",
                "width", width,
                "height", height,
                "crop", "fill", // Crop để fit kích thước
                "quality", "auto:good"
            )
        );

        return (String) uploadResult.get("secure_url");
    }

    public void deleteImage(String publicId) throws IOException {
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
}