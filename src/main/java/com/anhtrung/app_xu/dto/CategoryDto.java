package com.anhtrung.app_xu.dto;

import com.anhtrung.app_xu.domain.WasteType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private WasteType type;
    private String description;
    private String notes;
    private Double price;
    private String image;
}
