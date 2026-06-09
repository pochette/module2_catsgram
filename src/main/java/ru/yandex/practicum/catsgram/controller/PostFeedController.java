package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class PostFeedController {
    private final PostService postService;

    @Autowired
    public PostFeedController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/feed/friends")
    public List<Post> getFriends(@RequestBody String input) {
        ObjectMapper objectMapper = new ObjectMapper();
        FriendsParam friendsParam;
        try {
            String paramsFromInput = objectMapper.readValue(input, String.class);
            friendsParam = objectMapper.readValue(paramsFromInput, FriendsParam.class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Невалидные значения в строке body Json", e);
        }

        if (friendsParam != null) {
            List<Post> result = new ArrayList<>();

            for (String email : friendsParam.friends()) {
                result.addAll(postService.findPostsByEmails(email, friendsParam.sort, friendsParam.size));
            }

            log.debug("Получен запрос на получение ленты друзей с параметрами: sort={}, size={}, friends={}", friendsParam.sort, friendsParam.size, friendsParam.friends());
            return result;
        } else {
            throw new RuntimeException("ошибка при создании DTO FriendsParam");
        }

    }

    private record FriendsParam(String sort, Integer size, List<String> friends) {
    }
}



