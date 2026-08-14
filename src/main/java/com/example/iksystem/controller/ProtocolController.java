package com.example.iksystem.controller;

import com.example.iksystem.dto.protocol.ProtocolCreateDto;
import com.example.iksystem.dto.protocol.ProtocolResponseDto;
import com.example.iksystem.dto.protocol.ProtocolUpdateDto;
import com.example.iksystem.service.ProtocolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * Bu sınıf Protocol Controller nesnesini temsil eder.
 */
@RestController
@RequestMapping("/api/v1/admin/protocols")
@RequiredArgsConstructor
@Tag(name = "Protocols management", description = "Endpoints for managing protocols")
public class ProtocolController {

    private final ProtocolService protocolService;

    @Operation(summary = "Yeni protokol oluşturur")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProtocolResponseDto> createProtocol(
            @RequestPart("dto") @Valid ProtocolCreateDto dto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {

        ProtocolResponseDto response = protocolService.createProtocol(dto, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Mevcut protokolü günceller")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProtocolResponseDto> updateProtocol(
            @PathVariable UUID id,
            @RequestPart("dto") @Valid ProtocolUpdateDto dto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {

        ProtocolResponseDto response = protocolService.updateProtocol(id, dto, files);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Protokol durumunu (ACTIVE/PASSIVE) değiştirir")
    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<Void> toggleProtocolStatus(@PathVariable UUID id) {
        protocolService.toggleProtocolStatus(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Tüm protokolleri sayfalı olarak getirir")
    @GetMapping
    public ResponseEntity<Page<ProtocolResponseDto>> getAllProtocolsForAdmin(
            @PageableDefault(page = 0, size = 100, sort = "title", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ProtocolResponseDto> responses = protocolService.getAllProtocolsForAdmin(pageable);
        return ResponseEntity.ok(responses);
    }
}