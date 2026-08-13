package com.example.iksystem.dto.user;

import com.example.iksystem.enums.model.constant.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bu sınıf User Create Dto nesnesini temsil eder.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateDto {

    // JSON isteğinde 'name_surname' veya 'nameSurname' gelse de Java tarafında camelCase çalışır
    @JsonProperty("name_surname")
    @NotBlank(message = "Name and surname cannot be blank")
    private String nameSurname;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Role cannot be null")
    private Role role;

    @Builder.Default
    @NotNull(message = "isActive cannot be null")
    private Boolean isActive = true;
}