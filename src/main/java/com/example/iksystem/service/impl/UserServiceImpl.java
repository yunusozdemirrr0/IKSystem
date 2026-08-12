package com.example.iksystem.service.impl;

import com.example.iksystem.dto.user.UserCreateDto;
import com.example.iksystem.dto.user.UserResponseDto;
import com.example.iksystem.dto.user.UserUpdateDto;
import com.example.iksystem.entity.UsersEntity;
import com.example.iksystem.enums.model.constant.Role;

import com.example.iksystem.exception.AlreadyExistsException;
import com.example.iksystem.exception.ResourceNotFoundException;
import com.example.iksystem.repository.UserRepository;
import com.example.iksystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    @Transactional
    public UserResponseDto createUser(UserCreateDto dto) {
        if(userRepository.existsByEmailIgnoreCase(dto.getEmail())) {
            throw new AlreadyExistsException("User with this email already exists");
        }
        UsersEntity usersEntity = UsersEntity.builder()
                .nameSurname(dto.getName_surname())
                .email(dto.getEmail())
                .role(dto.getRole())
                .isActive(true)
                .build();
        UsersEntity savedUser = userRepository.save(usersEntity);
        return mapToUserResponseDto(savedUser);
    }




    @Override
    @Transactional
    public UserResponseDto updateUser(UUID id, UserUpdateDto dto) {
        UsersEntity user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (userRepository.existsByEmailIgnoreCaseAndIdNot(dto.getEmail(), id)) {
            throw new AlreadyExistsException("User with this email already exists");
        }
        user.setNameSurname(dto.getName_surname());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        UsersEntity updatedUser = userRepository.save(user);
        return mapToUserResponseDto(updatedUser);
    }
    @Override
    public UserResponseDto getUserById(UUID id) {
        UsersEntity user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return mapToUserResponseDto(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::mapToUserResponseDto).toList();
    }

    @Override
    public List<UserResponseDto> getUsersByRole(Role role) {
        return userRepository.findAllByRole(role).stream().map(this::mapToUserResponseDto).toList();
    }


    @Override
    @Transactional
    public void toggleUserStatus(UUID id) {
        UsersEntity user = userRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setActive(!user.isActive());

        userRepository.save(user);
    }
    private UserResponseDto mapToUserResponseDto(UsersEntity user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name_surname(user.getNameSurname())
                .email(user.getEmail())
                .role(user.getRole())
                .isActive(user.isActive())
                .build();

    }
}
