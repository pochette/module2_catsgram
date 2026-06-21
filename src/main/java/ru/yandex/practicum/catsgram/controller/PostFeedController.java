package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.IncorrectParameterException;
import ru.yandex.practicum.catsgram.model.FeedParams;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.FeedService;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.yandex.practicum.catsgram.Constants.SORTS;

@RestController()
@RequestMapping("/feed")
public class PostFeedController {

    private final FeedService feedService;

    public PostFeedController(FeedService feedService) {
        this.feedService = feedService;

    }


    @GetMapping
    List<Post> getFriendsFeed(@RequestParam ("userId") String userId, @RequestParam(defaultValue = "10") int max) {
        return feedService.getFeedFor(userId, max);
    }

    @GetMapping("/json")
    List<Post> getFriendsFeedJson(@RequestParam ("userId") String userId, @RequestParam(defaultValue = "10") int max) {
        return feedService.getFeedForJson(userId, max);
    }


//
//    public Collection<Post> findPostByUser(String authorId, Integer size, String sort) {
//        return feedService.findPostsByUser(authorId)
//                .stream()
//                .sorted((p0, p1) -> {
//                    int comp = p0.getCreationDate().compareTo(p1.getCreationDate());
//                    if(sort.equals("desc")) {
//                        comp = -1 * comp;
//                    }
//                    return comp;
//                })
//                .limit(size)
//                .collect(Collectors.toList());
//    }

}
