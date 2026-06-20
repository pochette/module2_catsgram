package ru.yandex.practicum.catsgram.dao;

import ru.yandex.practicum.catsgram.model.User;

import java.util.Optional;
import java.util.Set;

public interface UserDao {
    Optional<User> findUserById(String id);
    Optional<User> findUserByLogin(String login);
    Set<User> findAll();
}
