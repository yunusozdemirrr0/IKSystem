package com.example.iksystem.dto.category;

import com.example.iksystem.AlreadyExistsException;
import com.example.iksystem.ResourceNotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CategoryUpdateDto {
    @NotBlank(message = "categoryName cannot be blank")
    @Size(min = 2, max = 50, message = "categoryName must be between 2 and 50 characters")
    private String categoryName;
    private String icon;
    @NotNull(message = "isActive cannot be null")
    private Boolean isActive;


    }













