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

    // CategoryRepository, JpaRepository arayüzünü genişleterek CategoriesEntity varlıkları için temel CRUD işlemlerini sağlar. UUID, varlıkların benzersiz tanımlayıcıları olarak kullanılır.
    // Bu arayüz, kategori adının büyük/küçük harf duyarsız olarak var olup olmadığını kontrol etmek için özel sorgular içerir.
    public boolean existsByCategoryNameIgnoreCase(String categoryName);
    // Bu yöntem, kategori adının büyük/küçük harf duyarsız olarak var olup olmadığını kontrol eder. Eğer kategori adı mevcutsa true, aksi takdirde false döner.
    public boolean existsByCategoryNameIgnoreCaseAndIdNot(String categoryName, UUID id);

    // Bu yöntem, belirli bir kategori adının başka bir kategori ile çakışıp çakışmadığını kontrol eder. Eğer aynı ada sahip başka bir kategori varsa true, aksi takdirde false döner.
    public List<CategoriesEntity> findAllByIsActiveTrue();


    UUID id(UUID id);



}
