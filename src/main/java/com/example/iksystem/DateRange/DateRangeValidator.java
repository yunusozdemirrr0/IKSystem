package com.example.iksystem.DateRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;
/**
 * Bu sınıf Date Range Validator nesnesini temsil eder.
 */

public class DateRangeValidator implements ConstraintValidator<ValideDateRange, Object> {

    private String startDateField;
    private String endDateField;
    // Bu metod, doğrulayıcıyı başlatmak için kullanılır ve başlangıç ve bitiş tarih alanlarını alır.
    @Override
    public void initialize(ValideDateRange constraintAnnotation) {

        this.startDateField = constraintAnnotation.startDateField();
        this.endDateField = constraintAnnotation.endDateField();


    }
    // Bu metod, verilen nesnenin geçerli olup olmadığını kontrol eder.
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        try { // BeanWrapperImpl kullanarak nesnenin alanlarına erişim sağlanır.
            BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);
            Object startDate = beanWrapper.getPropertyValue(startDateField);
            Object endDate = beanWrapper.getPropertyValue(endDateField);
            // Eğer başlangıç veya bitiş tarihi null ise, @NotNull tarafından işlenmesi için true döndürülür.
            if (startDate == null || endDate == null) {
                return true; // Let @NotNull handle null values
            }
            // Eğer alanlar LocalDate tipinde değilse, IllegalArgumentException fırlatılır.
            if (!(startDate instanceof java.time.LocalDate) || !(endDate instanceof java.time.LocalDate)) {
                throw new IllegalArgumentException("Fields must be of type LocalDate");
            }
            // Başlangıç tarihinin bitiş tarihinden sonra olup olmadığını kontrol eder ve sonucu döndürür.
            return !(((java.time.LocalDate) startDate)).isAfter((java.time.LocalDate) endDate);
        } catch (Exception e) { // Alanlara erişim sırasında bir hata oluşursa, RuntimeException fırlatılır.
            throw new RuntimeException("Error accessing fields: " + e.getMessage(), e);
        }
    }

}
