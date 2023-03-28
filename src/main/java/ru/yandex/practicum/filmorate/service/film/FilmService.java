package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FilmService {
    List<Film> findAll();

    void create(Film film);

    void update(Film film);

    Film findById(long id);

    Film addLike(long id, long userId);

    Film deleteLike(long id, long userId);

    List<User> findPopularFilms(int count);
}
