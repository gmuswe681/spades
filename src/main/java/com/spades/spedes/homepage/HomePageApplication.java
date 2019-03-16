package com.spades.spedes.homepage;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HomePageApplication {

    @RequestMapping("/")
    public String home() {
        return "Hello World!";
    }

}