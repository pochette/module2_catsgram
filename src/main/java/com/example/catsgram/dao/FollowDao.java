package com.example.catsgram.dao;

import com.example.catsgram.model.Post;

import java.util.List;

public interface FollowDao {
    List<Post> getFollowFeed(String userId, int max);
}
