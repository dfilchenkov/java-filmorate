package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> userMap = new HashMap<>();
    private long countId;

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public void create(User user) {
        ++countId;
        user.setId(countId);

        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        userMap.put(user.getId(), user);
    }

    @Override
    public void update(User user) {
        if (!userMap.containsKey(user.getId())) {
            throw new UserNotFoundException(String.format("Не удалось обновить! User c id: %s не существует в базе",
                    user.getId()));
        }

        userMap.put(user.getId(), user);
    }

    @Override
    public User findById(long id) {
        if (!userMap.containsKey(id)) {
            throw new UserNotFoundException(String.format("Не удалось найти пользовтеля! User c id: %s не существует в базе", id));
        }
        return userMap.get(id);
    }

    @Override
    public User addToFriends(long id, long friendId) {
        if (!userMap.containsKey(id))
            throw new UserNotFoundException(String.format("Не удалось добавить в друзья! User c id: %s не существует в базе", id));
        if (!userMap.containsKey(friendId))
            throw new UserNotFoundException(String.format("Не удалось  добавить в друзья! User c id: %s не существует в базе", friendId));

        userMap.entrySet().stream()
                .filter(user -> user.getKey() == id)
                .map(Map.Entry::getValue).peek(user -> user.getFriends().add(friendId))
                .forEach(user -> userMap.put(id, user));

        userMap.entrySet().stream()
                .filter(friend -> friend.getKey() == friendId)
                .map(Map.Entry::getValue).peek(friend -> friend.getFriends().add(id))
                .forEach(friend -> userMap.put(friendId, friend));

        return userMap.get(id);
    }

    @Override
    public User deleteFromFriends(long id, long friendId) {
        if (!userMap.containsKey(id))
            throw new UserNotFoundException(String.format("Не удалось удалить из друзей! User c id: %s не существует в базе", id));
        if (!userMap.containsKey(friendId))
            throw new UserNotFoundException(String.format("Не удалось удалить из друзей! User c id: %s не существует в базе", friendId));

        userMap.entrySet().stream()
                .filter(user -> user.getKey() == id)
                .map(Map.Entry::getValue).peek(user -> user.getFriends().remove(friendId))
                .forEach(user -> userMap.put(id, user));

        userMap.entrySet().stream()
                .filter(friend -> friend.getKey() == friendId)
                .map(Map.Entry::getValue).peek(friend -> friend.getFriends().remove(id))
                .forEach(friend -> userMap.put(friendId, friend));

        return userMap.get(id);
    }

    @Override
    public List<User> getAllFriends(long id) {
        if (!userMap.containsKey(id)) {
            throw new UserNotFoundException(String.format("Не удалось найти пользовтеля! User c id: %s не существует в базе", id));
        }

        Set<Long> friendsIds = userMap.get(id).getFriends();
        return friendsIds.stream().map(userMap::get).collect(Collectors.toList());
    }

    @Override
    public List<User> getCrossFriends(long id, long otherId) {
        if (!userMap.containsKey(id))
            throw new UserNotFoundException(String.format("Не удалось найти друзей! User c id: %s не существует в базе", id));
        if (!userMap.containsKey(otherId))
            throw new UserNotFoundException(String.format("Не удалось найти друзей! User c id: %s не существует в базе", otherId));

        Set<Long> friendsUser = new HashSet<>(userMap.get(id).getFriends());
        Set<Long> friendsOther = new HashSet<>(userMap.get(otherId).getFriends());
        friendsUser.retainAll(friendsOther);

        return friendsUser.stream().map(userMap::get).collect(Collectors.toList());
    }
}
