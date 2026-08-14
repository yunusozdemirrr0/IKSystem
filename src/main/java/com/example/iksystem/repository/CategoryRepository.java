package com.example.iksystem.repository;

import com.example.iksystem.entity.CategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Bu arayüz Category Repository davranışlarını tanımlar.
 */
@Repository
public interface CategoryRepository extends JpaRepository<CategoriesEntity, UUID> {

    // Aktif ve aynı ada sahip kategori var mı kontrolü (Oluşturma için)
    boolean existsByCategoryNameIgnoreCaseAndIsActiveTrue(String categoryName);

    // Başka bir ID'ye sahip, aktif ve aynı ada sahip kategori var mı kontrolü (Güncelleme için)
    boolean existsByCategoryNameIgnoreCaseAndIsActiveTrueAndIdNot(String categoryName, UUID id);

    // Aktif kategorileri listeleme
    List<CategoriesEntity> findAllByIsActiveTrue();
}