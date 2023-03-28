package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody User user) {
        userService.create(user);
        log.info("User c id: {} и email: {} добавлен", user.getId(), user.getEmail());
        return user;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User update(@Valid @RequestBody User user) {
        userService.update(user);
        log.info("User c id: {} обновлен", user.getId());
        return user;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User findById(@PathVariable("id") long id) {
        User user = userService.findById(id);
        log.info("User c id: {} найден", id);
        return user;
    }

    @PutMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public User addFriends(@PathVariable("id") long id, @PathVariable("friendId") long friendId) {
        User user = userService.addToFriends(id, friendId);
        log.info("User c id: {} добавил в друзья User с id: {} ", id, friendId);
        return user;
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public User deleteFriends(@PathVariable("id") long id, @PathVariable("friendId") long friendId) {
        User user = userService.deleteFromFriends(id, friendId);
        log.info("User c id: {} удалил из друзей User с id: {} ", id, friendId);
        return user;
    }

    @GetMapping("/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllFriends(@PathVariable("id") long id) {
        return userService.getAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}") // TODO писок друзей общих с другим пользователем
    @ResponseStatus(HttpStatus.OK)
    public List<User> getCrossFriends(@PathVariable("id") long id, @PathVariable("otherId") long otherId) {
        return userService.getCrossFriends(id, otherId);
    }
}
