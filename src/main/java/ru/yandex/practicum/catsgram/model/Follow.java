package ru.yandex.practicum.catsgram.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Follow {
    private String authorId;
    private String followerId;
}
