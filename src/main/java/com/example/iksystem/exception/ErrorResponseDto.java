package com.example.iksystem.exception;

import lombok.*;

import java.time.LocalDateTime;
/**
 * Bu sınıf Error Response Dto nesnesini temsil eder.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
