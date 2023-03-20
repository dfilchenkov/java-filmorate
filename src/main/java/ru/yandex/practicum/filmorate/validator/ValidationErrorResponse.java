package ru.yandex.practicum.filmorate.validator;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ValidationErrorResponse {
    private final String objectName;
    private final String fieldName;
    private final String message;
}
