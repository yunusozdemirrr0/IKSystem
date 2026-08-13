package com.example.iksystem.controller;

import com.example.iksystem.service.UserService;
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
/**
 * Bu sınıf User Controller nesnesini temsil eder.
 */

@RestController
@RequestMapping()
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;

    /// Bu metod, yeni bir kullanıcı oluşturmak için HTTP POST isteğini işler.
    @PostMapping("/api/admin/users")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateDto dto) {
        UserResponseDto createdUser = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    /// Bu metod, bir kullanıcıyı güncellemek için HTTP PUT isteğini işler.
    @PutMapping("/api/admin/users/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable("id") UUID id, @Valid @RequestBody UserUpdateDto dto) {
        UserResponseDto updatedUser = userService.updateUser(id, dto);
        return ResponseEntity.ok(updatedUser);
    }

    /// Bu metod, bir kullanıcıyı getirmek için HTTP GET isteğini işler.
    @GetMapping("api/users/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") UUID id) {
        UserResponseDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    /// Bu metod, tüm kullanıcıları getirmek için HTTP GET isteğini işler.
    @GetMapping("api/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(@RequestParam(required = false) Role role) {
        if (role != null) { // Eğer role parametresi sağlanmışsa, belirtilen role sahip kullanıcıları getirir.
            List<UserResponseDto> usersByRole = userService.getUsersByRole(role);
            return ResponseEntity.ok(usersByRole);
        } else {// Eğer role parametresi sağlanmamışsa, tüm kullanıcıları getirir.
            List<UserResponseDto> allUsers = userService.getAllUsers();
            return ResponseEntity.ok(allUsers);
        }
    }

    /// Bu metod, bir kullanıcıyı etkinleştirme veya devre dışı bırakmak için HTTP PATCH isteğini işler.
    @PatchMapping("/api/admin/users/{id}/toggle-status")
    public ResponseEntity<Void> toggleUserStatus(@PathVariable("id") UUID id) {
        userService.toggleUserStatus(id);
        return ResponseEntity.noContent().build();

    }

}
