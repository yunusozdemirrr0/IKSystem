package com.example.iksystem.controller;

import com.example.iksystem.UserService;
import com.example.iksystem.dto.user.UserCreateDto;
import com.example.iksystem.dto.user.UserResponseDto;
import com.example.iksystem.dto.user.UserUpdateDto;
import com.example.iksystem.enums.model.constant.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateDto dto) {
        UserResponseDto createdUser = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable("id") UUID id, @Valid @RequestBody UserUpdateDto dto) {
        UserResponseDto updatedUser = userService.updateUser(id, dto);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") UUID id) {
        UserResponseDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(@RequestParam(required = false) Role role) {
        if (role != null) {
            List<UserResponseDto> usersByRole = userService.getUsersByRole(role);
            return ResponseEntity.ok(usersByRole);
        } else {
            List<UserResponseDto> allUsers = userService.getAllUsers();
            return ResponseEntity.ok(allUsers);
        }
    }

    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<Void> toggleUserStatus(@PathVariable("id") UUID id) {
        userService.toggleUserStatus(id);
        return ResponseEntity.noContent().build();

    }

}
