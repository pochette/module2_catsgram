package ru.yandex.practicum.catsgram.model;

import lombok.*;

import java.util.Objects;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor

public class User {
    private String id;

    private String username;

    private String nickname;



    //    @Email
//    private String email;
//    private LocalDate birthdate;


}