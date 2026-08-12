package com.example.iksystem.DateRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class DateRangeValidator implements ConstraintValidator<ValideDateRange, Object> {

    private String startDateField;
    private String endDateField;
    @Override
    public void initialize(ValideDateRange constraintAnnotation) {

        this.startDateField = constraintAnnotation.startDateField();
        this.endDateField = constraintAnnotation.endDateField();


    }
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        try {
            BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);
            Object startDate = beanWrapper.getPropertyValue(startDateField);
            Object endDate = beanWrapper.getPropertyValue(endDateField);

            if (startDate == null || endDate == null) {
                return true; // Let @NotNull handle null values
            }

            if (!(startDate instanceof java.time.LocalDate) || !(endDate instanceof java.time.LocalDate)) {
                throw new IllegalArgumentException("Fields must be of type LocalDate");
            }

            return !(((java.time.LocalDate) startDate)).isAfter((java.time.LocalDate) endDate);
        } catch (Exception e) {
            throw new RuntimeException("Error accessing fields: " + e.getMessage(), e);
        }
    }

}
