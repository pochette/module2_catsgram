package ru.yandex.practicum.catsgram.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
@Data
@RequiredArgsConstructor
public class Post {

    private Integer id;
    private final String author; // автор
    private final Instant creationDate = Instant.now(); // дата создания
    private String description; // описание
    private String photoUrl; // url-адрес фотографии
}