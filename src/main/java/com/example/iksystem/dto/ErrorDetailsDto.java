package com.example.iksystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
/**
 * Bu sınıf Error Details Dto nesnesini temsil eder.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ErrorDetailsDto {
    private String message;
    private LocalDateTime timestamp;
    private String details;
    private Integer status;





}
