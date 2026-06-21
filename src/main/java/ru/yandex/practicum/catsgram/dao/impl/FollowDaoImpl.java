package ru.yandex.practicum.catsgram.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.catsgram.dao.FollowDao;
import ru.yandex.practicum.catsgram.dao.PostDao;
import ru.yandex.practicum.catsgram.dao.UserDao;
import ru.yandex.practicum.catsgram.model.Follow;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class FollowDaoImpl implements FollowDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserDao userDao;
    private final PostDao postDao;

    public FollowDaoImpl(JdbcTemplate jdbcTemplate, UserDao userDao, PostDao postDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
        this.postDao = postDao;
    }


    @Override
    public List<Post> getFeedFor(String userId, int max) {
        // получаем все подписки пользователя
        String sql = "select * from cat_follow where follower_id = ?";
        List<Follow> follows = jdbcTemplate.query(sql, (rs, rowNum) -> makeFollow(rs), userId);

        // выгружаем авторов на которых подписан пользователь
        Set<User> authors = follows.stream()
                .map(Follow::getAuthorId)
                .map(userDao::findUserById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        if(authors.isEmpty()) {
            return Collections.emptyList();
        }

        // выгружаем посты полученных выше авторов
        return authors.stream()
                .map(postDao::findPostsByUser)
                .flatMap(Collection::stream)
                // сортируем от новых к старым
                .sorted(Comparator.comparing(Post::getCreationDate).reversed())
                // отбрасываем лишнее
                .limit(max)
                .collect(Collectors.toList());
    }

    public List<Post> getFeedForJson(String userId, int max) {
        String sql = "SELECT p.*\n" +
                "FROM cat_post AS p\n" +
                "JOIN cat_follow f ON\n" +
                "    f.author_id = p.author_id\n" +
                "where f.follower_id = ?\n" +
                "ORDER BY p.creation_date DESC\n" +
                "LIMIT ?\n" +
                "\n";

        return jdbcTemplate.query(sql, ((rs, rowNum) -> makePost(rs)), userId, max);

    }

    private Follow makeFollow(ResultSet rs) throws SQLException {
        return new Follow(rs.getString("author_id"), rs.getString("follower_id"));
    }
    private Post makePost(ResultSet rs) throws SQLException {
        return Post.builder()
                .id(rs.getInt("id"))
                .description(rs.getString("description"))
                .photoUrl(rs.getString("photo_url"))
                .author(userDao.findUserByLogin(rs.getString("author_id")).orElse(new User()))
                .creationDate(rs.getTimestamp("creation_date").toLocalDateTime())
                .build();
    }
}


