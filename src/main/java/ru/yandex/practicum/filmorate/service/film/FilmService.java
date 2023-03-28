package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FilmService {
    List<Film> findAll();
    void create(Film film);
    void update(Film film);
    Film findById(long id);
    void addLike(User user);
    void deleteLike(User user);
    List<User> findPopularFilms(int size);
}