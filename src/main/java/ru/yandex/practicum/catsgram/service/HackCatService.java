package ru.yandex.practicum.catsgram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class HackCatService {
    public static final String JDBC_URL = "jdbc:postgresql://localhost:5432/cats";
    public static final String JDBC_DRIVER = "org.postgresql.Driver";
    public static final String JDBC_USER = "kitty";
    public static final String JDBC_PASSWORD = "purrrrrr";
    private final Logger log = LoggerFactory.getLogger(HackCatService.class);

    public Optional<String> doHackNow() {
        List<String> catWordList = Arrays.asList("meow", "purr", "purrrrrr", "zzz");
        for (String password : catWordList) {
            try {
                tryPassword(password);
                return Optional.of(password);
            } catch (Exception e) {
                log.info("Неправильный пароль для базы данных: {}", password);

            }
        }
        return Optional.empty();
    }

    public void tryPassword(String jdbcPassword) {
        DriverManagerDataSource dataSourceConst = new DriverManagerDataSource();
        dataSourceConst.setDriverClassName(JDBC_DRIVER);
        dataSourceConst.setUsername(JDBC_USER);
        dataSourceConst.setUrl(JDBC_URL);
        dataSourceConst.setPassword(jdbcPassword);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceConst);
        jdbcTemplate.execute("SELECT 1;");

    }

}
