package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.validator.ValidationErrorResponse;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionValidationControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorResponse>> mismatchException(MethodArgumentNotValidException exception) {

        final List<ValidationErrorResponse> errorResponses = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> new ValidationErrorResponse(error.getObjectName(), error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        errorResponses.forEach(error -> log.error("Ошибка валидации {}. Поле: {}. Ошибка: {}", error.getObjectName(), error.getFieldName(), error.getMessage()));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponses);

    }
}

