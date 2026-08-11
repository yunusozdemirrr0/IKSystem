package com.example.iksystem.controller;

import com.example.iksystem.ProtocolService;
import com.example.iksystem.dto.user.UserProtocolDetailDto;
import com.example.iksystem.dto.user.UserProtocolListDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/protocols")
@RequiredArgsConstructor
@Tag(name = "User Protocol Controller", description = "Controller for user protocol operations")
public class UserProtocolController {
    private final ProtocolService protocolService;

    @GetMapping("/api/v1/protocols")
    public ResponseEntity<Page<UserProtocolListDto>> getActiveProtocolsForUser(@RequestParam(required = false) String search,
                                                                               @RequestParam(required = false) UUID categoryId, Pageable pageable) {
        Page<UserProtocolListDto> protocols = protocolService.getActiveProtocolsForUser(search, categoryId, pageable);
        return ResponseEntity.ok(protocols);
    }

    @GetMapping("/api/v1/protocols/{id}")
    @Operation(summary = "Get protocol detail for user", description = "Retrieve the details of a specific protocol for a user by its ID.")
    public ResponseEntity<UserProtocolDetailDto> getProtocolDetailForUser(@PathVariable UUID id) {
        UserProtocolDetailDto protocol = protocolService.getProtocolDetailForUser(id);
        return ResponseEntity.ok(protocol);
    }
}
