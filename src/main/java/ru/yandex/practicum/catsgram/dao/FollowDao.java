package ru.yandex.practicum.catsgram.dao;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.catsgram.model.Post;

import java.util.List;

@Component
public interface FollowDao {
    List<Post> getFeedFor(String userId, int max);
    List<Post> getFeedForJson(String userId, int max);

}
