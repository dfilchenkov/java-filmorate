package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> filmMap = new HashMap<>();
    private int countId;

    @GetMapping
    public ResponseEntity<List<Film>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ArrayList<>(filmMap.values()));
    }

    @PostMapping
    public ResponseEntity<Film> create(@Valid @RequestBody Film film) {
        ++countId;
        film.setId(countId);

        filmMap.put(film.getId(), film);

        log.info("Film с id: {} и name: {} добавлен", film.getId(), film.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(film);
    }

    @PutMapping
    public ResponseEntity<Film> update(@Valid @RequestBody Film film) {
        if (!filmMap.containsKey(film.getId())) {
            log.error("Не удалось обновить! Film c id: {} не существует в базе", film.getId());
            throw new FilmNotFoundException(String.format("Не удалось обновить! Film c id: %s не существует в базе",
                    film.getId()));
        }

        filmMap.put(film.getId(), film);

        log.info("Film с id: {} обновлен", film.getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(film);
    }
}
