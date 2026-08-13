package com.example.iksystem.service;

import com.example.iksystem.dto.user.UserCreateDto;
import com.example.iksystem.dto.user.UserResponseDto;
import com.example.iksystem.dto.user.UserUpdateDto;
import com.example.iksystem.enums.model.constant.Role;
import java.util.List;
import java.util.UUID;
/**
 * Bu arayüz User Service davranışlarını tanımlar.
 */

public interface UserService{

    UserResponseDto createUser(UserCreateDto dto);
    UserResponseDto updateUser(UUID id, UserUpdateDto dto);
    UserResponseDto getUserById(UUID id);
    List<UserResponseDto> getAllUsers();
    List<UserResponseDto> getUsersByRole(Role role);
    void toggleUserStatus(UUID id);

}
