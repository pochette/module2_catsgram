package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final HashMap<String, User> users = new HashMap<>();

    @PostMapping
    public User createUser(@RequestBody User user) {
        if (users.containsKey(user.getEmail())) {
            throw new UserAlreadyExistException("Пользователь с таким e-mail уже существует");
        } else if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidEmailException("Адрес почты равен null или пустой строке");
        } else
            log.debug("Добавлен пользователь: {}", user);
        users.put(user.getEmail(), user);
        return user;
    }

    @GetMapping
    public Collection<User> getUsers() {
        log.debug("Количество пользователей: {}", users.size());
        return users.values();
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidEmailException("Адрес почты равен null или пустой строке");
        } else {
            log.debug("Обновлен пользователь: {}", user);
            users.put(user.getEmail(), user);
            return user;
        }
    }

}

