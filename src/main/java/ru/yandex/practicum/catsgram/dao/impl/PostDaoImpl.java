package ru.yandex.practicum.catsgram.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.catsgram.dao.PostDao;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.service.UserService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Component
public class PostDaoImpl implements PostDao {
    private final UserService userService;
    private final JdbcTemplate jdbcTemplate;

    public PostDaoImpl(UserService userService, JdbcTemplate jdbcTemplate) {
        this.userService = userService;
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public Collection<Post> findPostsByUser(User user) {
        String sql = "SELECT * FROM cat_post WHERE author_id = ? " +
                "ORDER BY creation_date DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makePost(user, rs), user.getId());


    }

    private Post makePost(User user, ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        LocalDateTime creationDate = rs.getTimestamp("creation_date").toLocalDateTime();
        String description = rs.getString("description");
        String photoUrl = rs.getString("photo_url");

        return Post.builder().id(id).author(user).creationDate(creationDate).photoUrl(photoUrl).description(description).build();
    }


}

//private Integer id;
//private final User author; // автор
//private final LocalDate creationDate; // дата создания
//private String description; // описание
//private String photoUrl; // url-адрес фотографии
//}

//
//@Override
//public Collection<com.example.catsgram.model.Post> findPostsByUser(com.example.catsgram.model.User user) {
//    // метод принимает в виде аргумента строку запроса, преобразователь и аргумент — id пользователя
//    String sql = "select * from cat_post where author_id = ? order by creation_date desc";
//
//    return jdbcTemplate.query(sql, (rs, rowNum) -> makePost(user, rs), user.getId());
//}
//
//private com.example.catsgram.model.Post makePost(com.example.catsgram.model.User user, ResultSet rs) throws SQLException {
//    // используем конструктор, методы ResultSet
//    // и готовое значение user
//    Integer id = rs.getInt("id");
//    String description = rs.getString("description");
//    String photoUrl = rs.getString("photo_url");
//
//    // Получаем дату и конвертируем её из sql.Date в time.LocalDate
//    LocalDate creationDate = rs.getDate("creation_date").toLocalDate();
//
//    return new com.example.catsgram.model.Post(id, user, description, photoUrl, creationDate);
//}