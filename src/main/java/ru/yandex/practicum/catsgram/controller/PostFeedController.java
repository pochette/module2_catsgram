package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;

import static ru.yandex.practicum.catsgram.Constants.SORTS;

@Slf4j
@RestController()
@RequestMapping("/feed/friends")
public class PostFeedController {

    private final PostService postService;

    public PostFeedController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    List<Post> getFriendsFeed(@RequestBody FeedParams feedParams) {
        if (!SORTS.contains(feedParams.getSort()) || feedParams.getFriends().isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (feedParams.getSize() == null || feedParams.getSize() <= 0) {
            throw new IllegalArgumentException();
        }

        List<Post> result = new ArrayList<>();
        for (String friendEmail : feedParams.getFriends()) {
            result.addAll(postService.findAllByUserEmail(friendEmail, feedParams.getSize(), feedParams.getSort()));
        }
        return result;
    }
//    @PostMapping("/feed/friends")
//    public List<Post> getFriends(@RequestBody String input) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        FeedParams friendsParam;
//        try {
//            String paramsFromInput = objectMapper.readValue(input, String.class);
//            friendsParam = objectMapper.readValue(paramsFromInput, FeedParams.class);
//
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Невалидные значения в строке body Json", e);
//        }
//
//        if (friendsParam != null) {
//            List<Post> result = new ArrayList<>();
//
//            for (String email : friendsParam.getFriends()) {
//                result.addAll(postService.findAllByUserEmail(email, friendsParam.getSize(), friendsParam.getSort()));
//            }
//
//            log.debug("Получен запрос на получение ленты друзей с параметрами: sort={}, size={}, friends={}", friendsParam.sort, friendsParam.size, friendsParam.getFriends());
//            return result;
//        } else {
//            throw new RuntimeException("ошибка при создании DTO FeedParams");
//        }
//
//    }

    @Data
    private static class FeedParams {
        private final String sort;
        private final Integer size;
        private final List<String> friends;




    }



}
