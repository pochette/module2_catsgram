package ru.yandex.practicum.catsgram.model;

import lombok.*;

import java.util.Objects;

@Data
@NoArgsConstructor
@Builder
public class User {
    private String id;

    private String username;

    private String nickname;

    public User(String id, String username, String nickname) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
    }
    //    @Email
//    private String email;
//    private LocalDate birthdate;


}