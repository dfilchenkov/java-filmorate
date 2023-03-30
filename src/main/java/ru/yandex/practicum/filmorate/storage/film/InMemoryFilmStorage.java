package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> filmMap = new HashMap<>();
    private long countId;

    private UserService userService;

    public InMemoryFilmStorage(UserService userService) {
        this.userService = userService;
    }

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

    @Override
    public Film findById(long id) {
        if (!filmMap.containsKey(id)) {
            throw new FilmNotFoundException(String.format("Не удалось найти фильм! Film c id: %s не существует в базе", id));
        }
        return filmMap.get(id);
    }

    @Override
    public Film addLike(long id, long userId) {
        if (!filmMap.containsKey(id)) {
            throw new FilmNotFoundException(String.format("Не удалось поставить like! Film c id: %s не существует в базе", id));
        }

        User user = userService.findById(userId);

        filmMap.entrySet().stream()
                .filter(film -> film.getKey() == id)
                .map(Map.Entry::getValue).peek(film -> film.getLikes().add(user.getId()))
                .forEach(film -> filmMap.put(id, film));

        return filmMap.get(id);
    }

    @Override
    public Film deleteLike(long id, long userId) {
        if (!filmMap.containsKey(id)) {
            throw new FilmNotFoundException(String.format("Не удалось поставить like! Film c id: %s не существует в базе", id));
        }

        User user = userService.findById(userId);

        filmMap.entrySet().stream()
                .filter(film -> film.getKey() == id)
                .map(Map.Entry::getValue).peek(film -> film.getLikes().remove(user.getId()))
                .forEach(film -> filmMap.put(id, film));

        return filmMap.get(id);
    }

    @Override
    public List<Film> findPopularFilms(int count) {
        return filmMap.values().stream()
                .sorted(Comparator.comparing(o -> o.getLikes().size() * -1))
                .limit(count)
                .collect(Collectors.toList());
    }
}
