package com.example.iksystem.service.impl;

import com.example.iksystem.*;
import com.example.iksystem.dto.protocol.ProtocolCreateDto;
import com.example.iksystem.dto.protocol.ProtocolResponseDto;
import com.example.iksystem.dto.protocol.ProtocolUpdateDto;
import com.example.iksystem.entity.CompanyEntity;
import com.example.iksystem.enums.model.constant.ProtocolStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProtocolServiceImpl implements ProtocolService {

    private final ProtocolRepository protocolRepository;
    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ProtocolResponseDto createProtocol(ProtocolCreateDto dto, List<MultipartFile> files) {
        validateDates(dto.getBeginDate(), dto.getEndDate());

        CompanyEntity company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + dto.getCompanyId()));

        CategoriesEntity category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));

        ProtocolsEntity protocolsEntity = ProtocolsEntity.builder()
                .title(dto.getTitle())
                .discountPercentage(dto.getDiscountPercent())
                .discountDetailsText(dto.getDiscountDetailText())
                .beginDate(dto.getBeginDate())
                .endDate(dto.getEndDate())
                .protocolStatus(dto.getStatus() == ProtocolStatus.ACTIVE)
                .company(company)
                .category(category)
                .build();

        ProtocolsEntity savedEntity = protocolRepository.save(protocolsEntity);
        return mapToResponseDto(savedEntity);
    }

    @Override
    @Transactional
    public ProtocolResponseDto updateProtocol(UUID id, ProtocolUpdateDto dto, List<MultipartFile> files) {
        ProtocolsEntity existingProtocol = protocolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Protocol not found with id: " + id));

        LocalDate newBeginDate = dto.getBeginDate() != null ? dto.getBeginDate() : existingProtocol.getBeginDate();
        LocalDate newEndDate = dto.getEndDate() != null ? dto.getEndDate() : existingProtocol.getEndDate();

        validateDates(newBeginDate, newEndDate);

        if (dto.getCompanyId() != null) {
            CompanyEntity company = companyRepository.findById(dto.getCompanyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + dto.getCompanyId()));
            existingProtocol.setCompany(company);
        }

        if (dto.getCategoryId() != null) {
            CategoriesEntity category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));
            existingProtocol.setCategory(category);
        }

        existingProtocol.setTitle(dto.getTitle());
        existingProtocol.setDiscountPercentage(dto.getDiscountPercent());
        existingProtocol.setDiscountDetailsText(dto.getDiscountDetailText());
        existingProtocol.setBeginDate(newBeginDate);
        existingProtocol.setEndDate(newEndDate);

        if (dto.getStatus() != null) {
            existingProtocol.setProtocolStatus(dto.getStatus() == ProtocolStatus.ACTIVE);
        }

        ProtocolsEntity updatedEntity = protocolRepository.save(existingProtocol);
        return mapToResponseDto(updatedEntity);
    }

    private void validateDates(LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            throw new IllegalArgumentException("Begin date and end date are required");
        }
        if (beginDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Begin date cannot be after end date");
        }
    }

    private ProtocolResponseDto mapToResponseDto(ProtocolsEntity entity) {
        return ProtocolResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .discountPercent(entity.getDiscountPercentage())
                .discountDetailText(entity.getDiscountDetailsText())
                .beginDate(entity.getBeginDate())
                .endDate(entity.getEndDate())
                .status(entity.isProtocolStatus() ? ProtocolStatus.ACTIVE : ProtocolStatus.PASSIVE)
                .companyId(entity.getCompany() != null ? entity.getCompany().getId() : null)
                .categoryId(entity.getCategory() != null ? entity.getCategory().getId() : null)
                .build();
    }
    @Override
    @Transactional
    public void toggleProtocolStatus(UUID id) {
        ProtocolsEntity existingProtocol = protocolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Protocol not found with id: " + id));

        existingProtocol.setProtocolStatus(!existingProtocol.isProtocolStatus());
        protocolRepository.save(existingProtocol);

    }

    @Override
    public Page<ProtocolResponseDto> getAllProtocolsForAdmin(Pageable pageable) {
        return protocolRepository.findAll(pageable)
                .map(this::mapToResponseDto);
    }
}