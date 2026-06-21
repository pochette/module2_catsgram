package ru.yandex.practicum.catsgram.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.catsgram.dao.FollowDao;
import ru.yandex.practicum.catsgram.dao.PostDao;
import ru.yandex.practicum.catsgram.dao.UserDao;
import ru.yandex.practicum.catsgram.model.Follow;
import ru.yandex.practicum.catsgram.model.Post;

import java.util.List;

@Component
public class FollowDaoImpl implements FollowDao {
    private final UserDao userDao;
    private final PostDao postDao;
    private final JdbcTemplate jdbcTemplate;

    public FollowDaoImpl(UserDao userDao, PostDao postDao, JdbcTemplate jdbcTemplate) {
        this.userDao = userDao;
        this.postDao = postDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Post> getFeedFor(String userId, int max) {
        return List.of();
    }

    private Follow getFollowByUserId(String userId, String followerId) {
        return Follow.builder().userId(userId).followerId(followerId).build();

    }
}
