package com.estsoft.blogjpa.domain.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class UserViewController {

    @GetMapping("/login")
    public String login() {
        log.info("login()");
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        log.info("signup()");
        return "signup";
    }
}
