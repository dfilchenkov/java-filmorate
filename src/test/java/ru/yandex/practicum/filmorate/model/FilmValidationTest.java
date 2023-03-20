package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmValidationTest {

    @Autowired
    private FilmController filmController;

    @Test
    @DisplayName("1) Проверка обновления несуществующего фильма с id: 999")
    void createFilmUnknownUpdateTest() {
        Film unknownFilm = Film.builder()
                .id(999)
                .name("Film name")
                .description("Description")
                .releaseDate(LocalDate.of(1900, 3, 25))
                .duration(200)
                .build();

        FilmNotFoundException exception = assertThrows(FilmNotFoundException.class, () -> filmController.update(unknownFilm));
        assertEquals(exception.getMessage(), "Не удалось обновить! Film c id: 999 не существует в базе");
    }
}