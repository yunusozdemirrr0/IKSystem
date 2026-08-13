package com.example.iksystem.service;

import com.example.iksystem.dto.company.CompanyCreateDto;
import com.example.iksystem.dto.company.CompanyResponseDto;
import com.example.iksystem.dto.company.CompanyUpdateDto;

import java.util.List;
import java.util.UUID;
/**
 * Bu arayüz Company Service davranışlarını tanımlar.
 */

public interface CompanyService {
    CompanyResponseDto createCompany(CompanyCreateDto dto);

    CompanyResponseDto updateCompany( UUID id, CompanyUpdateDto dto);

    CompanyResponseDto getCompanyById(UUID id);

    List<CompanyResponseDto> getAllCompanies();

    List<CompanyResponseDto> getActiveCompaniesForAdmin();
}
