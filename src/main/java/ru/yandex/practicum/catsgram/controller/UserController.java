package ru.yandex.practicum.catsgram.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.service.UserService;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.debug("Получен запрос на создание пользователя с email: {}", user.getEmail());
        return userService.createUser(user);
    }

    @GetMapping
    @ResponseBody
    public Collection<User> findAll() {
        log.debug("Получен запрос на поиск всех пользователей");
        return userService.findAll();
    }

    @GetMapping("/user/{email}")
    public Optional<User> findByEmail(@Valid @PathVariable String email) {
        log.debug("Получен запрос на поиск пользователя по email: {}", email);
        return userService.findAll().stream()
                .filter(x -> x.getEmail().equals(email))
                .findFirst();
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) {
        log.debug("Получен запрос на обновление пользователя с email: {}", user.getEmail());
        return userService.updateUser(user);
    }

}