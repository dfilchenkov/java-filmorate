package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.error.ErrorResponse;
import ru.yandex.practicum.filmorate.model.error.ValidationErrorResponse;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionValidationControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ValidationErrorResponse> mismatchException(MethodArgumentNotValidException exception) {

        final List<ValidationErrorResponse> errorResponses = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> new ValidationErrorResponse(error.getObjectName(), error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        errorResponses.forEach(error -> log.error("Ошибка валидации {}. Поле: {}. Ошибка: {}", error.getObjectName(), error.getFieldName(), error.getMessage()));
        return errorResponses;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse throwableException(UserNotFoundException exception) {
        log.error(exception.getMessage());
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(FilmNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse throwableException(FilmNotFoundException exception) {
        log.error(exception.getMessage());

        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse throwableException(Throwable throwable) {
        log.error("Unchecked Throwable: {}", throwable.getMessage());

        return new ErrorResponse(throwable.getMessage());
    }
}

