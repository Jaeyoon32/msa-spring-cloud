package com.kidd.msasample.msauserservice.controller;

import com.kidd.msasample.msauserservice.vo.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {

    private Environment env;

    @Autowired
    private Greeting greeting;
    @Autowired
    public UserController(Environment env) {
        this.env = env;
    }
    private String message;
    @GetMapping(value = "/health-check")
    public String status() {
        return "It's working in User service";
    }

    @GetMapping(value = "/welcome")
    public String welcome() {
        return greeting.getMessage();
        //return this.env.getProperty("greeting.message");
    }
}
