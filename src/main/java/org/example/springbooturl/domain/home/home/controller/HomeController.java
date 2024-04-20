package org.example.springbooturl.domain.home.home.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "domain/home";
    }

    @GetMapping("/chat")
    public String chat() {
        return "domain/chat";
    }
}
