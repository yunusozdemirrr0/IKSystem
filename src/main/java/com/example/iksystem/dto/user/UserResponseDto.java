package com.example.iksystem.dto.user;

import com.example.iksystem.entity.UsersEntity;
import com.example.iksystem.enums.model.constant.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Bu sınıf User Response Dto nesnesini temsil eder.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

    private UUID id;

    @JsonProperty("name_surname")
    private String nameSurname;

    private String email;

    private Role role;

    private Boolean isActive;

    /**
     * Entity nesnesini doğrudan Response DTO'ya dönüştüren yardımcı metot.
     */
    public static UserResponseDto fromEntity(UsersEntity entity) {
        if (entity == null) {
            return null;
        }
        return UserResponseDto.builder()
                .id(entity.getId())
                .nameSurname(entity.getNameSurname())
                .email(entity.getEmail())
                .role(entity.getRole())
                .isActive(entity.getIsActive())
                .build();
    }
}