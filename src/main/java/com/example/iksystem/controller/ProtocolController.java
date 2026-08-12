package com.example.iksystem.controller;

import com.example.iksystem.service.ProtocolService;
import com.example.iksystem.dto.protocol.ProtocolCreateDto;
import com.example.iksystem.dto.protocol.ProtocolResponseDto;
import com.example.iksystem.dto.protocol.ProtocolUpdateDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/protocols")
@RequiredArgsConstructor
@Tag(name = "Protocols management", description = "Endpoints for managing protocols")

public class ProtocolController {
    private final ProtocolService protocolService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProtocolResponseDto> createProtocol(@RequestPart("dto")
                                                              @Valid ProtocolCreateDto dto,
                                                              @RequestPart(value = "files",
                                                                      required=false) List<MultipartFile> files)  {

        ProtocolResponseDto response = protocolService.createProtocol(dto, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProtocolResponseDto> updateProtocol(@PathVariable UUID id, @RequestPart("dto")@Valid ProtocolUpdateDto dto, @RequestPart(value = "files", required=false) List<MultipartFile> files) {
        ProtocolResponseDto response = protocolService.updateProtocol(id,dto, files);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<ProtocolResponseDto> toggleProtocolStatus(@PathVariable UUID id) {
        protocolService.toggleProtocolStatus(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping

    public ResponseEntity<Page<ProtocolResponseDto>> getAllProtocolsForAdmin(@PageableDefault(size = 10,sort = "beginDate") Pageable pageable) {
        Page<ProtocolResponseDto> responses = protocolService.getAllProtocolsForAdmin(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}
