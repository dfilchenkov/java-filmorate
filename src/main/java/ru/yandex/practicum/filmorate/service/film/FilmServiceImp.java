package ru.yandex.practicum.filmorate.service.film;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;

@Service
public class FilmServiceImp implements FilmService {

    private FilmStorage filmStorage;

    public FilmServiceImp(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    @Override
    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    @Override
    public void create(Film film) {
        filmStorage.create(film);
    }

    @Override
    public void update(Film film) {
        filmStorage.update(film);
    }

    @Override
    public Film findById(long id) { // TODO
        return null;
    }

    @Override
    public Film addLike(long id, long userId) {
        return null;
    }

    @Override
    public Film deleteLike(long id, long userId) {
        return null;
    }

    @Override
    public List<User> findPopularFilms(int count) {
        return null;
    }


}
