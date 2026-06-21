package ru.yandex.practicum.catsgram.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Follow {
    private String userId;
    private String followerId;
}
