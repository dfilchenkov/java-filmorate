package ru.yandex.practicum.filmorate.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Аннотация валидации даты релиза
 *
 * @version 1.0
 * @autor Dmitry Filchenkov
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReleaseDateValidator.class)
@Documented
public @interface ReleaseDate {
    String message() default "{NotSpace.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    short year() default 1800;

    byte month() default 1;

    byte day() default 1;
}
