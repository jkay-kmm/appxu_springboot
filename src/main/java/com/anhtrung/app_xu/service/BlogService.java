package com.anhtrung.app_xu.service;

import com.anhtrung.app_xu.domain.Blog;
import com.anhtrung.app_xu.dto.BlogDto;
import com.anhtrung.app_xu.repo.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    public List<BlogDto> getAllBlogs() {
        return blogRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private BlogDto toDto(Blog blog) {
        return BlogDto.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .description(blog.getDescription())
                .time(blog.getTime())
                .image(blog.getImage())
                .build();
    }
}
