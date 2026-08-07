package com.example.iksystem;

import com.example.iksystem.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {
    boolean existsByCompanyNameIgnoreCase(String companyName);
    boolean existsByCompanyNameIgnoreCaseAndIdNot(String companyName, UUID id);
}
