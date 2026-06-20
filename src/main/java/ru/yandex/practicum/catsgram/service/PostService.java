package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.dao.PostDao;
import ru.yandex.practicum.catsgram.model.Post;

import java.util.Collection;

@Service
public class PostService {
    private final UserService userService;
    private final PostDao postDao;

    public PostService(UserService userService, PostDao postDao) {
        this.userService = userService;
        this.postDao = postDao;
    }
    public Collection<Post> findPostsByUser(String userId) {
        return postDao.findPostsByUser(userService.findUserById(userId).get());

    }


}
