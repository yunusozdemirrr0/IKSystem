package com.example.iksystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * Bu sınıf Resource Not Found Exception nesnesini temsil eder.
 */
@ResponseStatus(HttpStatus.NOT_FOUND) // Bu anotasyon, bu istisna fırlatıldığında HTTP yanıt durum kodunu 404 olarak ayarlar.
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }


}
