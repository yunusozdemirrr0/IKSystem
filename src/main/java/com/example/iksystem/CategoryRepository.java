package com.example.iksystem;

import com.example.iksystem.CategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface CategoryRepository extends JpaRepository<CategoriesEntity, UUID> {

    public boolean existsByCategoryNameIgnoreCase(String categoryName);
    public boolean existsByCategoryNameIgnoreCaseAndIdNot(String categoryName, UUID id);

    public List<CategoriesEntity> findAllByIsActiveTrue();


    UUID id(UUID id);



}
