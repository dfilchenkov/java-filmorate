package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    void create(User user);

    void update(User user);

    User findById(long id);

    User addToFriends(long id, long friendId);

    User deleteFromFriends(long id, long friendId);

    List<User> getAllFriends(long id);

    List<User> getCrossFriends(long id, long otherId);
}
