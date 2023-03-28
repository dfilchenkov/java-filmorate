package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.validator.ReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Film {
    private Long id;
    @NotBlank(message = "Названия фильма не должно быть пустым")
    private String name;
    @Size(max = 200, message = "Длинна описания не должна превышать 200 знаков")
    private String description;
    @ReleaseDate(year = 1895, month = 12, day = 28, message = "Дата релиза - не раньше 28.12.1895")
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной")
    private int duration;
    private Set<Long> likes = new HashSet<>();
}
