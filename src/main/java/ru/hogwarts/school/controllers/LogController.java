package ru.hogwarts.school.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
public class LogController {

    @Autowired
    private ServletWebServerApplicationContext server;

    @GetMapping("/port")
    public int getServerPort() {
        return server.getWebServer().getPort();
    }

    @GetMapping()
    public int getInt() {
        return Stream.iterate(1, a -> a +1)
                .parallel() //Это же так работает?)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b );
    }
}
