package com.example.iksystem.dto.user;

import com.example.iksystem.UsersEntity;
import com.example.iksystem.enums.model.constant.Role;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    @NotBlank(message = "Name and surname cannot be blank")
    private String name_surname;
    @Email(message = "Email should be valid")
    private String email;

    private Role role;
    @NotNull(message = "isActive cannot be null")
    private Boolean isActive;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    public static UserResponseDto fromUsersEntity(String name_surname, String email, Role role, Boolean isActive, UUID id) {
        return UserResponseDto.builder()
                .name_surname(name_surname)
                .email(email)
                .role(role)
                .isActive(isActive)
                .id(id)
                .build();
    }

}


