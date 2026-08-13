package com.example.iksystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * Bu sınıf Bad Request Exception nesnesini temsil eder.
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }

    // Bu sınıfın bir başka yapıcı metodu, bir mesaj ve bir neden (Throwable) alır.
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
