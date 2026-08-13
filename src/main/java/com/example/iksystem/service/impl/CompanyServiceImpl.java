package com.example.iksystem.service.impl;

import com.example.iksystem.dto.company.CompanyCreateDto;
import com.example.iksystem.dto.company.CompanyResponseDto;
import com.example.iksystem.dto.company.CompanyUpdateDto;
import com.example.iksystem.entity.CompanyEntity;
import com.example.iksystem.exception.AlreadyExistsException;
import com.example.iksystem.exception.ResourceNotFoundException;
import com.example.iksystem.repository.CompanyRepository;
import com.example.iksystem.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Bu sınıf Company Service Impl nesnesini temsil eder.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public CompanyResponseDto createCompany(CompanyCreateDto dto) {
        if (companyRepository.existsByCompanyNameIgnoreCase(dto.getCompanyName())) {
            throw new AlreadyExistsException("Company already exists!");
        }

        CompanyEntity companyEntity = CompanyEntity.builder()
                .companyName(dto.getCompanyName())
                .isActive(dto.getIsActive() != null ? dto.getIsActive() : true)
                .email(dto.getEmail())
                .address(dto.getAddress())
                .telephone(dto.getTelephone())
                .logoUrl(dto.getLogoUrl())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();

        CompanyEntity savedCompany = companyRepository.save(companyEntity);

        return mapToResponseDto(savedCompany);
    }

    @Override
    public CompanyResponseDto getCompanyById(UUID id) {
        CompanyEntity company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found!"));

        return mapToResponseDto(company);
    }

    @Override
    @Transactional
    public CompanyResponseDto updateCompany(UUID id, CompanyUpdateDto dto) {
        CompanyEntity company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found!"));

        if (companyRepository.existsByCompanyNameIgnoreCaseAndIdNot(dto.getCompanyName(), id)) {
            throw new AlreadyExistsException("Company already exists!");
        }

        // Tüm alanların güncellenmesi sağlanıyor
        company.setCompanyName(dto.getCompanyName());
        company.setEmail(dto.getEmail());
        company.setAddress(dto.getAddress());
        company.setTelephone(dto.getTelephone());
        company.setLogoUrl(dto.getLogoUrl());
        company.setLatitude(dto.getLatitude());
        company.setLongitude(dto.getLongitude());
        if (dto.getIsActive() != null) {
            company.setIsActive(dto.getIsActive());
        }

        CompanyEntity updatedCompany = companyRepository.save(company);
        return mapToResponseDto(updatedCompany);
    }

    @Override
    public List<CompanyResponseDto> getAllCompanies() {
        return companyRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    @Override
    public List<CompanyResponseDto> getActiveCompaniesForAdmin() {
        return companyRepository.findAll()
                .stream()
                .filter(CompanyEntity::getIsActive)
                .map(this::mapToResponseDto)
                .toList();
    }

    /**
     * Entity - Response DTO dönüşümünü tek noktadan yöneten yardımcı metot.
     */
    private CompanyResponseDto mapToResponseDto(CompanyEntity entity) {
        return CompanyResponseDto.builder()
                .id(entity.getId())
                .companyName(entity.getCompanyName())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .telephone(entity.getTelephone())
                .logoUrl(entity.getLogoUrl())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .isActive(entity.getIsActive())
                .build();
    }
}