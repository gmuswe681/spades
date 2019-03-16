package com.spades.spedes.error;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class ErrorPage implements ErrorController {

    @RequestMapping("/error")
    public String errorString() {
        return "It looks like a problem occurred. Try again later.";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}