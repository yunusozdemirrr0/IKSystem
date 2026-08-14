package com.example.iksystem.service.impl;

import com.example.iksystem.dto.category.CategoryCreateDto;
import com.example.iksystem.dto.category.CategoryResponseDto;
import com.example.iksystem.dto.category.CategoryUpdateDto;
import com.example.iksystem.entity.CategoriesEntity;
import com.example.iksystem.exception.AlreadyExistsException;
import com.example.iksystem.exception.ResourceNotFoundException;
import com.example.iksystem.repository.CategoryRepository;
import com.example.iksystem.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Bu sınıf Category Service Impl nesnesini temsil eder.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public CategoryResponseDto createCategory(CategoryCreateDto dto) {
        // Yalnızca aynı ada sahip AKTİF bir kategori varsa hata fırlatır
        if (categoryRepository.existsByCategoryNameIgnoreCaseAndIsActiveTrue(dto.getCategoryName())) {
            throw new AlreadyExistsException("Active category with this name already exists!");
        }

        CategoriesEntity categoriesEntity = CategoriesEntity.builder()
                .icon(dto.getIcon())
                .categoryName(dto.getCategoryName())
                .isActive(true)
                .build();

        CategoriesEntity savedCategory = categoryRepository.save(categoriesEntity);
        return mapToResponseDto(savedCategory);
    }

    @Override
    public CategoryResponseDto getCategoryById(UUID id) {
        CategoriesEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
        return mapToResponseDto(category);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public List<CategoryResponseDto> getActiveCategories() {
        return categoryRepository.findAllByIsActiveTrue()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Transactional
    @Override
    public void toggleCategoryStatus(UUID id) {
        CategoriesEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));

        // Pasiften aktife çekerken aynı ada sahip başka aktif kategori var mı kontrolü
        if (!category.getIsActive() && categoryRepository.existsByCategoryNameIgnoreCaseAndIsActiveTrueAndIdNot(category.getCategoryName(), id)) {
            throw new AlreadyExistsException("Cannot activate category. Another active category with this name already exists!");
        }

        category.setIsActive(!category.getIsActive());
        categoryRepository.save(category);
    }

    @Transactional
    @Override
    public CategoryResponseDto updateCategory(UUID id, CategoryUpdateDto dto) {
        CategoriesEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));

        // Eğer güncellenen kategori aktif olarak kalacaksa/olacaksa ve aynı ada sahip başka aktif kategori varsa hata fırlatır
        boolean targetIsActive = dto.getIsActive() != null ? dto.getIsActive() : category.getIsActive();
        if (targetIsActive && categoryRepository.existsByCategoryNameIgnoreCaseAndIsActiveTrueAndIdNot(dto.getCategoryName(), id)) {
            throw new AlreadyExistsException("Active category with this name already exists!");
        }

        category.setCategoryName(dto.getCategoryName());
        category.setIcon(dto.getIcon());
        if (dto.getIsActive() != null) {
            category.setIsActive(dto.getIsActive());
        }

        CategoriesEntity updatedCategory = categoryRepository.save(category);
        return mapToResponseDto(updatedCategory);
    }

    private CategoryResponseDto mapToResponseDto(CategoriesEntity category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .icon(category.getIcon())
                .isActive(category.getIsActive())
                .build();
    }
}