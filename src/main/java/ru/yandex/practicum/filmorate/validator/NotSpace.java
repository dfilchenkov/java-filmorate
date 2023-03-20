package ru.yandex.practicum.filmorate.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Аннотация валидации строкового поля на наличие пробела
 *
 * @version 1.0
 * @autor Dmitry Filchenkov
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotSpaceValidator.class)
@Documented
public @interface NotSpace {
    String message() default "{NotSpace.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
