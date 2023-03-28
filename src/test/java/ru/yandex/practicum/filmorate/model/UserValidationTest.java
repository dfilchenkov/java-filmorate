package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserValidationTest {

    @Autowired
    private UserController userController;

    @Test
    @DisplayName("1) Проверка обновления несуществующего user с id: 9999")
    void createUserUnknownUpdateTest() {
        User unknownUser = User.builder()
                .id(9999L)
                .login("doloreUpdate")
                .name("est adipisicing")
                .email("mail@yandex.ru")
                .birthday(LocalDate.of(1976, 9, 2))
                .build();

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userController.update(unknownUser));
        assertEquals(exception.getMessage(), "Не удалось обновить! User c id: 9999 не существует в базе");
    }
}