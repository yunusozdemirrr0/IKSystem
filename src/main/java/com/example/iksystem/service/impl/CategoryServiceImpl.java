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


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public  class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;


    @Transactional
    @Override
    public CategoryResponseDto createCategory(CategoryCreateDto dto) {
        if (categoryRepository.existsByCategoryNameIgnoreCase(dto.getCategoryName())) {
            throw new AlreadyExistsException("Category already exists!");
        }
        CategoriesEntity categoriesEntity = CategoriesEntity.builder()
                .icon(dto.getIcon())
                .categoryName(dto.getCategoryName())
                .isActive(true)
                .build();
        CategoriesEntity savedCategory = categoryRepository.save(categoriesEntity);
        return CategoryResponseDto.builder()
                .id(savedCategory.getId())
                .categoryName(savedCategory.getCategoryName())
                .icon(savedCategory.getIcon())
                .isActive(savedCategory.getIsActive())
                .build();



        }

    @Override
    @Transactional
    public CategoryResponseDto getCategoryById(UUID id) {
        CategoriesEntity category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
        return CategoryResponseDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .icon(category.getIcon())
                .isActive(category.getIsActive())
                .build();
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        List<CategoriesEntity> categories = categoryRepository.findAll();
        return categories.stream().map(category -> CategoryResponseDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .icon(category.getIcon())
                .isActive(category.getIsActive())
                .build()).toList();
    }

    @Override
    public List<CategoryResponseDto> getActiveCategories() {
        List<CategoriesEntity> categories = categoryRepository.findAllByIsActiveTrue();
        return categories.stream().map(category -> CategoryResponseDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .icon(category.getIcon())
                .isActive(category.getIsActive())
                .build()).toList();
    }

    @Override
    @Transactional
    public void toggleCategoryStatus(UUID id) {
        CategoriesEntity category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
        category.setIsActive(!category.getIsActive());
        categoryRepository.save(category);
    }
    @Transactional
    @Override
    public CategoryResponseDto updateCategory(UUID id, CategoryUpdateDto dto) {
        CategoriesEntity category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
        if (categoryRepository.existsByCategoryNameIgnoreCaseAndIdNot(dto.getCategoryName(), id)) {
            throw new AlreadyExistsException("Category already exists!");
        }
        category.setCategoryName(dto.getCategoryName());
        category.setIcon(dto.getIcon());
        category.setIsActive(dto.getIsActive());
        CategoriesEntity updatedCategory = categoryRepository.save(category);
        return CategoryResponseDto.builder()
                .id(updatedCategory.getId())
                .categoryName(updatedCategory.getCategoryName())
                .icon(updatedCategory.getIcon())
                .isActive(updatedCategory.getIsActive())
                .build();
    }

}

