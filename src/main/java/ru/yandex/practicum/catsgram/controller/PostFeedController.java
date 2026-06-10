package ru.yandex.practicum.catsgram.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.exception.IncorrectParameterException;
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
        if (feedParams.friends().isEmpty()) {
            throw new IncorrectParameterException("emails");
        }
        if (!SORTS.contains(feedParams.sort())) {
            throw new IncorrectParameterException("sort");
        }
        if (feedParams.size() == null || feedParams.size() <= 0) {
            throw new IncorrectParameterException("size");
        }

        List<Post> result = new ArrayList<>();
        for (String friendEmail : feedParams.friends()) {
            result.addAll(postService.findAllByUserEmail(friendEmail, feedParams.size(), feedParams.sort()));
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

    private record FeedParams(String sort, Integer size, List<String> friends) {
    }

}
