package ru.yandex.practicum.catsgram.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.catsgram.model.User;

import java.util.Optional;
import java.util.Set;

@Component
public class UserDaoImpl implements UserDao {

    private static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findUserById(String id) {
        SqlRowSet userRow = jdbcTemplate.queryForRowSet("SELECT * FROM cat_user WHERE id = ?", id);
        if(userRow.next()) {
            log.info("Найден пользователь: {}, {}", userRow.getString("id"), userRow.getString("username"));

        }

        User user = new User();
        user.setId(id);
        return Optional.of(user);
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        //TODO ДОбавить реализацию findUserByLogin
        return Optional.empty();
    }

    @Override
    public Set<User> findAll() {
        return Set.of();
    }
}
