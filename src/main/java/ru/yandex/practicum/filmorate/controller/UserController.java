package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> userMap = new HashMap<>();
    private int countId;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ArrayList<>(userMap.values()));
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        ++countId;
        user.setId(countId);

        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        userMap.put(user.getId(), user);
        log.info("User c id: {} и email: {} добавлен", user.getId(), user.getEmail());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(user);
    }

    @PutMapping
    public ResponseEntity<User> update(@Valid @RequestBody User user) {
        if (!userMap.containsKey(user.getId())) {
            throw new UserNotFoundException(String.format("Не удалось обновить! User c id: %s не существует в базе",
                    user.getId()));
        }

        userMap.put(user.getId(), user);
        log.info("User c id: {} обновлен", user.getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }
}
