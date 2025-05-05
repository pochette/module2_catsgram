package com.example.catsgram.service;

import org.springframework.stereotype.Service;
import com.example.catsgram.dao.FollowDao;
import com.example.catsgram.model.Post;

import java.util.List;

@Service
public class FeedService {
    private final FollowDao followDao;

    public FeedService(FollowDao followDao) {
        this.followDao = followDao;
    }

    public List<Post> getFeedFor(String userId, int max) {
        return followDao.getFollowFeed(userId, max);
    }
}
