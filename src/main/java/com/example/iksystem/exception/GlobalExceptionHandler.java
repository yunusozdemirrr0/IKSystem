package com.example.iksystem.exception;

import com.example.iksystem.dto.ErrorDetailsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.List;
/**
 * Bu sınıf Global Exception Handler nesnesini temsil eder.
 */

@RestControllerAdvice // Bu sınıf, uygulama genelinde meydana gelen istisnaları yakalamak ve uygun HTTP yanıtlarını döndürmek için kullanılan bir global exception handler'dır. @RestControllerAdvice anotasyonu ile işaretlenmiştir, bu sayede tüm controller'lar için geçerli olur ve istisnaları merkezi olarak yönetir.
public class GlobalExceptionHandler{

    // Bu metod, ResourceNotFoundException istisnasını yakalar ve uygun bir HTTP yanıtı döndürür.
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
    // Bu metod, AlreadyExistsException istisnasını yakalar ve uygun bir HTTP yanıtı döndürür.
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
    // Bu metod, MethodArgumentNotValidException istisnasını yakalar ve uygun bir HTTP yanıtı döndürür.
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
    // Bu metod, AccessDeniedException istisnasını yakalar ve uygun bir HTTP yanıtı döndürür.
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetailsDto> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorDetailsDto errorDetails = ErrorDetailsDto.builder()
                .message(ex.getMessage())
                .timestamp(java.time.LocalDateTime.now())
                .details("Access denied")
                .status(HttpStatus.FORBIDDEN.value())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }
    // Bu metod, genel Exception istisnasını yakalar ve uygun bir HTTP yanıtı döndürür.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetailsDto> handleValidationException(Exception ex) {
        ErrorDetailsDto errorDetails = ErrorDetailsDto.builder()
                .message(ex.getMessage())
                .timestamp(java.time.LocalDateTime.now())
                .details("An unexpected error occurred")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    // Bu metod, BadRequestException ve FileStorageException istisnalarını yakalar ve uygun bir HTTP yanıtı döndürür.
    @ExceptionHandler({BadRequestException.class, FileStorageException.class})
    public  ResponseEntity<ErrorDetailsDto> handleBadRequestException(Exception ex, HttpStatus status) {
        ErrorDetailsDto errorDetails = ErrorDetailsDto.builder()
                .message(ex.getMessage())
                .timestamp(java.time.LocalDateTime.now())
                .details("An unexpected error occurred")
                .status(status.value())
                .build();
        return new ResponseEntity<>(errorDetails, status);
    }


}
