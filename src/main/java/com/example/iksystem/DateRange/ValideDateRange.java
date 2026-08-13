package com.example.iksystem.DateRange;

import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Bu anotasyon Valide Date Range için oözel işaretleme sağlar.
 */

@Target({java.lang.annotation.ElementType.TYPE}) // Bu anotasyon sınıf seviyesinde kullanılabilir.
@Retention(RetentionPolicy.RUNTIME)// Anotasyonun çalışma zamanında erişilebilir olmasını sağlar.
@Constraint(validatedBy = DateRangeValidator.class)// Bu anotasyonun doğrulama mantığını sağlayacak sınıfı belirtir.
//Bu anotasyon, bir sınıfın belirli alanlarının tarih aralığını doğrulamak için kullanılır. Başlangıç tarihi (startDateField) bitiş tarihinden (endDateField) önce olmalıdır. Eğer bu koşul sağlanmazsa, belirtilen hata mesajı (message) döndürülür. Ayrıca, bu anotasyon gruplar ve payload gibi ek bilgileri de taşıyabilir.
public @interface ValideDateRange {
    String message() default "beginDate must be before endDate";
    Class<?>[] groups() default {}; // Bu anotasyonun hangi gruplara ait olduğunu belirtir.
    Class<? extends jakarta.validation.Payload>[] payload() default {}; // Bu anotasyonun taşıyacağı ek bilgileri belirtir.
    String startDateField() default "beginDate"; // Başlangıç tarihini temsil eden alanın adını belirtir.
    String endDateField() default "endDate"; // Bitiş tarihini temsil eden alanın adını belirtir.
}
