package com.example.catsgram.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.catsgram.model.Post;
import com.example.catsgram.service.FeedService;

import java.util.List;

@RestController()
@RequestMapping("/feed")
public class PostFeedController {

    private final FeedService feedService;

    public PostFeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping
    List<Post> getFriendsFeed(@RequestParam("userId") String userId, @RequestParam(defaultValue = "10") int max) {
        return feedService.getFeedFor(userId, max);
    }
}