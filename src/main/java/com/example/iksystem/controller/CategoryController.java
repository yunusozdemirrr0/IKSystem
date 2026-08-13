package com.example.iksystem.controller;


import com.example.iksystem.service.CategoryService;
import com.example.iksystem.dto.category.CategoryCreateDto;
import com.example.iksystem.dto.category.CategoryResponseDto;
import com.example.iksystem.dto.category.CategoryUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.util.UUID;
/**
 * Bu sınıf Category Controller nesnesini temsil eder.
 */


@RestController
@RequestMapping()
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/api/admin/categories")

    /// Kategori oluştur
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody @Valid CategoryCreateDto dto) {
        CategoryResponseDto response = categoryService.createCategory(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
    /// Kategori güncelle
    @PutMapping("/api/admin/categories/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable UUID id, @RequestBody @Valid CategoryUpdateDto dto) {
        CategoryResponseDto response = categoryService.updateCategory(id, dto);
        return ResponseEntity.ok(response);

    }

    /// Kategori getir
    @GetMapping("/api/categories/{id}")
    public ResponseEntity<CategoryResponseDto> getCategory(@PathVariable UUID id) {
        CategoryResponseDto response = categoryService.getCategoryById(id);
        return ResponseEntity.ok(response);
    }
    /// Tüm kategorileri getir
    @GetMapping("/api/categories")
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        List<CategoryResponseDto> response = categoryService.getAllCategories();
        return ResponseEntity.ok(response);
    }
    /// Aktif kategorileri getir
    @GetMapping("/api/categories/active")
    public ResponseEntity<List<CategoryResponseDto>> getActiveCategories() {
        List<CategoryResponseDto> response = categoryService.getActiveCategories();
        return ResponseEntity.ok(response);
    }
    /// Kategori soft delete yap
    @PatchMapping("/api/admin/categories/{id}/toggle-status")
    public ResponseEntity<Void> toggleCategoryStatus(@PathVariable UUID id) {
        categoryService.toggleCategoryStatus(id);
        return ResponseEntity.noContent().build();
    }
}
