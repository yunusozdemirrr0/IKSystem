package com.example.iksystem;

import com.example.iksystem.dto.ErrorDetailsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailsDto> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorDetailsDto errorDetails = ErrorDetailsDto.builder()
                .message(ex.getMessage())
                .timestamp(java.time.LocalDateTime.now())
                .details("Resource not found")
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorDetailsDto> handleAlreadyExistsException(AlreadyExistsException ex) {
        ErrorDetailsDto errorDetails = ErrorDetailsDto.builder()
                .message(ex.getMessage())
                .timestamp(java.time.LocalDateTime.now())
                .details("Resource already exists")
                .status(HttpStatus.CONFLICT.value())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetailsDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
        ErrorDetailsDto errorDetails = ErrorDetailsDto.builder()
                .message("Invalid input data")
                .timestamp(java.time.LocalDateTime.now())
                .details("One or more validation errors occurred")
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}