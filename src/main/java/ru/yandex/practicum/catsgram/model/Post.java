package ru.yandex.practicum.catsgram.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder

public class Post {

    private Integer id;
    private final User author; // автор
    private final LocalDateTime creationDate; // дата создания
    private String description; // описание
    private String photoUrl; // url-адрес фотографии
}