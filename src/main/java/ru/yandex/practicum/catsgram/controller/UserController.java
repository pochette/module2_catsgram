package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private HashMap<String, User> users = new HashMap<>();

    @GetMapping
    public HashMap<String, User> getUsers() {
        return users;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        if (users.containsKey(user.getEmail())) {
            throw new UserAlreadyExistException("Пользователь с таким e-mail уже существует");
        } else if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidEmailException("Адрес почты равен null или пустой строке");
        } else
            users.put(user.getEmail(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidEmailException("Адрес почты равен null или пустой строке");
        } else
            users.put(user.getEmail(), user);
        return user;
    }

}

