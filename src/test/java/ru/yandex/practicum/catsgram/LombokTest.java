package ru.yandex.practicum.catsgram;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LombokTest {

    public static void main(String[] args) {
        String name = null;
        Person person = new Person("Andrew", 5, "Prof", "afhjkal@anbkj.tn");
        Person person1 = Person.builder().build();
//                Person.builder()
//                .age(3)
//                .email("burdak@mail.ru")
//                .profession("Professor")
//                .name("Andrew")
//                .profession("Another profession")
//                .build();

        System.out.println(person);
        System.out.println(person1);

        Person personCopy =person.toBuilder().age(-1).email("-asdfjhikl").build();
        System.out.println(personCopy);
        log.debug("debug");

    }

    @Slf4j
    @Value
    @Builder(toBuilder = true)

    private static class Person {
        @NonNull
        @Builder.Default
        String name = "Unknown name";
        @Builder.Default
        int age = 18;
        @Builder.Default
        String profession = "Unknown profession";
        @Builder.Default
        String email = "unknown@mail.com";

    }
}



