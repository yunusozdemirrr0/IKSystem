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
@Transactional(readOnly = true) // Sınıf düzeyinde varsayılan olarak tüm yöntemler için okuma işlemi yapılacağını belirtir.
public  class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;


    @Transactional
    @Override
    // Bu metod, yeni bir kategori oluşturur.
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

        // Yeni oluşturulan kategoriyi CategoryResponseDto nesnesine dönüştürerek döndürür.
        return CategoryResponseDto.builder()
                .id(savedCategory.getId())
                .categoryName(savedCategory.getCategoryName())
                .icon(savedCategory.getIcon())
                .isActive(savedCategory.getIsActive())
                .build();



        }

    @Override
    @Transactional

    // Bu metod, verilen ID'ye sahip kategoriyi getirir.
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
    // Bu metod, tüm kategorileri getirir.
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
    // Bu metod, aktif kategorileri getirir.
    public List<CategoryResponseDto> getActiveCategories() {
        List<CategoriesEntity> categories = categoryRepository.findAllByIsActiveTrue();
        return categories.stream().map(category -> CategoryResponseDto.builder() // Her bir kategori için CategoryResponseDto nesnesi oluşturur.
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .icon(category.getIcon())
                .isActive(category.getIsActive())
                .build()).toList();
    }

    @Override
    @Transactional
    // Bu metod, verilen ID'ye sahip kategoriyi etkinleştirir veya devre dışı bırakır.
    public void toggleCategoryStatus(UUID id) {
        CategoriesEntity category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
        category.setIsActive(!category.getIsActive());
        categoryRepository.save(category);
    }
    @Transactional
    @Override
    // Bu metod, verilen ID'ye sahip kategoriyi günceller.
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
