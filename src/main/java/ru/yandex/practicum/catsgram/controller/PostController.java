package ru.yandex.practicum.catsgram.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "/post")
    public Post create(@Valid @RequestBody Post post) {
        return postService.create(post);
    }

    @GetMapping("/posts")
    public List<Post> findAll(
            @RequestParam(value = "sort", defaultValue = "desc", required = false) String sort,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (!(sort.equals("desc") || sort.equals("asc"))) {
            throw new IllegalArgumentException();
        }
        if (size < 0) {
            throw new IllegalArgumentException();
        }
        Integer from = size * page;
        log.debug("Получен запрос на поиск всех постов с параметрами: sort={}, page={}, size={}", sort, page, size);
        return postService.findAll(sort, from, size);

    }

    @GetMapping("/posts/{postId}")
    public Post findById(@PathVariable("postId") Integer postId) {
        log.debug("Получен запрос на поиск поста по ID: {}", postId);
        return postService.findPostById(postId);
    }

    @GetMapping("/posts/search")
    public List<Post> searchPostsByAuthorAndDate(
            @RequestParam(value = "author", required = false) String author,
            @RequestParam (value = "date", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        log.debug("Получен запрос на поиск постов по автору и дате: author={}, date={}", author, date);
        return postService.searchPostsByAuthorAndDate(author, date);

    }

}