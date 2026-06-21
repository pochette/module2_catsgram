package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class SimpleController {

    public SimpleController(){

    }


    @GetMapping("/home")
    public String homePage() {
        return "Котограм";
    }
}
