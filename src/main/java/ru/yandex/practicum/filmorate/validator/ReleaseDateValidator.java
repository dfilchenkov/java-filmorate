package ru.yandex.practicum.filmorate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDate, LocalDate> {
    short year;
    byte month;
    byte day;

    @Override
    public void initialize(ReleaseDate constraintAnnotation) {
        this.year = constraintAnnotation.year();
        this.month = constraintAnnotation.month();
        this.day = constraintAnnotation.day();
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate.isAfter(LocalDate.of(year, month, day));
    }
}
