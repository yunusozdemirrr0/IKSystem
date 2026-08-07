package com.example.iksystem.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateDto {
    @NotBlank(message = "categoryName cannot be blank")
    @Size(min = 2, max = 50, message = "categoryName must be between 2 and 50 characters")
    private String categoryName;

    private String icon;



}
