package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> filmMap = new HashMap<>();
    private long countId;

    @Override
    public List<Film> findAll() {
        return new ArrayList<>(filmMap.values());
    }

    @Override
    public void create(Film film) {
        ++countId;
        film.setId(countId);
        filmMap.put(film.getId(), film);
    }

    @Override
    public void update(Film film) {
        if (!filmMap.containsKey(film.getId())) {
            throw new FilmNotFoundException(String.format("Не удалось обновить! Film c id: %s не существует в базе",
                    film.getId()));
        }

        filmMap.put(film.getId(), film);
    }
}
