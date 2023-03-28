package ru.yandex.practicum.filmorate.service.user;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
public class UserServiceImp implements UserService{
    private UserStorage userStorage;

    public UserServiceImp(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public List<User> findAll() {
        return userStorage.findAll();
    }

    @Override
    public void create(User user) {
        userStorage.create(user);
    }

    @Override
    public void update(User user) {
        userStorage.update(user);
    }

    @Override
    public User findById(long id) {
        return userStorage.findById(id);
    }

    @Override
    public User addToFriends(long id, long friendId) {
        return userStorage.addToFriends(id, friendId);
    }

    @Override
    public User deleteFromFriends(long id, long friendId) {
        return userStorage.deleteFromFriends(id, friendId);
    }

    @Override
    public List<User> getAllFriends(long id) {
        return userStorage.getAllFriends(id);
    }

    @Override
    public List<User> getCrossFriends(long id, long otherId) {
        return userStorage.getCrossFriends(id, otherId);
    }
}
