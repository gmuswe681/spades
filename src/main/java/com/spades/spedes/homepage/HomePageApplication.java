package com.spades.spedes.homepage;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HomePageApplication {

    @RequestMapping("/")
    public String home() {
        String result = "<html>\n";
        result += "<head></head>\n";
        result += "<body>\n";
        result += "<p>Hello World!</p>\n";
        result += "<a href=\"/logout\">Logout</a>\n";
        result += "</body>\n";
        result += "</html>";
        return result;
    }

}