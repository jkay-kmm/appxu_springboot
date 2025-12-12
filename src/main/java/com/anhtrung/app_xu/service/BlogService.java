package com.anhtrung.app_xu.service;

import com.anhtrung.app_xu.domain.Blog;
import com.anhtrung.app_xu.repo.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    public List<Map<String, Object>> getAllBlogs() {
        return blogRepository.findAll().stream()
                .map(this::toMap)
                .collect(Collectors.toList());
    }

    private Map<String, Object> toMap(Blog blog) {
        return Map.of(
                "id", blog.getId(),
                "title", blog.getTitle(),
                "time", blog.getTime(),
                "image", blog.getImage()
        );
    }
}
