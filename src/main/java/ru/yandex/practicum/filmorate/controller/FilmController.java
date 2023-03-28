package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Film> findAll() {
        return filmService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film create(@Valid @RequestBody Film film) {
        filmService.create(film);
        log.info("Film с id: {} и name: {} добавлен", film.getId(), film.getName());
        return film;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Film update(@Valid @RequestBody Film film) {
        filmService.update(film);
        log.info("Film с id: {} обновлен", film.getId());
        return film;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Film findById(@PathVariable("id") long id) {
        return filmService.findById(id);
    }

    @PutMapping("/{id}/like/{userId}") // TODO пользователь ставит лайк фильму
    @ResponseStatus(HttpStatus.OK)
    public Film addLike(@PathVariable("id") long id, @PathVariable("userId") long userId) {
        Film film = filmService.addLike(id, userId);
        log.info("Film с id: {} был поставлен like от User с id: {}", id, userId);
        return film;
    }

    @DeleteMapping("/{id}/like/{userId}") // TODO пользователь удаляет лайк
    @ResponseStatus(HttpStatus.OK)
    public Film deleteLike(@PathVariable("id") long id, @PathVariable("userId") long userId) {
        Film film = filmService.deleteLike(id, userId);
        log.info("У Film с id: {} был удален like от User с id: {}", id, userId);
        return film;
    }

    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10", required = false) int count) {
        return filmService.findPopularFilms(count);
    }
}
