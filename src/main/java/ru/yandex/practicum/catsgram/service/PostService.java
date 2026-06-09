package ru.yandex.practicum.catsgram.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.PostNotFoundException;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PostService {
    private static Integer globalId = 0;
    private final UserService userService;
    private final List<Post> posts = new ArrayList<>();

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    public Post create(Post post) {
        User postAuthor = userService.findUserByEmail(post.getAuthor());
        if (postAuthor == null) {
            throw new UserNotFoundException(String.format(
                    "Пользователь %s не найден",
                    post.getAuthor()));
        }
        post.setId(getNextId());
        log.debug("Создание нового поста: {}", post);
        posts.add(post);
        return post;
    }

    private static Integer getNextId() {
        log.debug("Генерация нового ID для поста: {}", globalId);
        return globalId++;
    }

    public List<Post> findAll(String sort, Integer from, Integer size) {
        log.debug("Получен запрос на поиск постов: sort={}, from={}, size={}", sort, from, size);
        return posts.stream().sorted((p1, p2) -> {
                    int comp = p1.getCreationDate().compareTo(p2.getCreationDate());
                    if (sort.equals("desc")) {
                        comp *= -1;
                    }
                    return comp;
                })
                .skip(from).limit(size).toList();
    }

    public Post findPostById(Integer id) {
        log.debug("Поиск поста по ID: {}", id);
        return posts.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new PostNotFoundException(String.format("Пост %d не найден.", id)));
    }

    public List<Post> findPostsByEmails(String email, String sort, Integer size) {
        log.debug("Получен запрос на поиск постов по email: email={}, sort={}, size={}", email, sort, size);
        return posts.stream()
                .filter(post -> email.equals(post.getAuthor())).sorted(
                        (p0, p1) -> {
                            int comp = p0.getCreationDate().compareTo(p1.getCreationDate());
                            if (sort.equals("desc")) {
                                comp *= -1;
                            }
                            return comp;
                        }).limit(size).toList();

    }

    public List<Post> searchPostsByAuthorAndDate(String author, LocalDate date) {
        log.debug("Получен запрос на поиск постов по автору и дате: author={}, date={}", author, date);

        return posts.stream()
                .filter(x -> x.getAuthor().equals(author) &&
                        LocalDate.ofInstant(x.getCreationDate(), ZoneId.systemDefault()).equals(date))
                .toList();
    }

}