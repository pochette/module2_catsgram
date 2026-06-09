package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;

import static ru.yandex.practicum.catsgram.Constants.SORTS;

@RestController()
@RequestMapping("/feed/friends")
public class PostFeedController {

    private final PostService postService;

    public PostFeedController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    List<Post> getFriendsFeed(@RequestBody FeedParams feedParams) {
        if (!SORTS.contains(feedParams.getSort()) || feedParams.getFriendsEmails().isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (feedParams.getSize() == null || feedParams.getSize() <= 0) {
            throw new IllegalArgumentException();
        }

        List<Post> result = new ArrayList<>();
        for (String friendEmail : feedParams.getFriendsEmails()) {
            result.addAll(postService.findAllByUserEmail(friendEmail, feedParams.getSize(), feedParams.getSort()));
        }
        return result;
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



}
