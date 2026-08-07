package com.example.iksystem;
import com.example.iksystem.dto.category.CategoryCreateDto;
import com.example.iksystem.dto.category.CategoryResponseDto;
import com.example.iksystem.dto.category.CategoryUpdateDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CategoryService {
    CategoryResponseDto createCategory(CategoryCreateDto dto);
    CategoryResponseDto updateCategory(UUID  id, CategoryUpdateDto dto);
    List<CategoryResponseDto> getActiveCategories();
    List<CategoryResponseDto> getAllCategories();
    CategoryResponseDto getCategoryById(UUID id);
    void toggleCategoryStatus(UUID id);



}
