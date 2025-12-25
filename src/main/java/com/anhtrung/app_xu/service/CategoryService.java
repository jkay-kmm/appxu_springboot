package com.anhtrung.app_xu.service;

import com.anhtrung.app_xu.domain.Category;
import com.anhtrung.app_xu.domain.WasteType;
import com.anhtrung.app_xu.dto.CategoryDto;
import com.anhtrung.app_xu.repo.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<CategoryDto> getCategoriesByType(WasteType type) {
        return categoryRepository.findByType(type).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .type(category.getType())
                .description(category.getDescription())
                .notes(category.getNotes())
                .price(category.getPrice())
                .image(category.getImage())
                .build();
    }
}
