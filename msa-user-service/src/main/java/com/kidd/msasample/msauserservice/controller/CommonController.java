package com.kidd.msasample.msauserservice.controller;

import com.kidd.msasample.msauserservice.vo.Greeting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/user-service")
public class CommonController {
    private Environment env;

    @Autowired
    private Greeting greeting;

    @Autowired
    public CommonController(Environment env) {
        this.env = env;
    }

    @GetMapping(value = "/health-check")
    public String status() {
        return String.format("It's working in User service on Port %s",
                env.getProperty("local.server.port"));
    }

    @GetMapping(value = "/welcome")
    public String welcome() {
        return greeting.getMessage();
        //return this.env.getProperty("greeting.message");
    }

    @GetMapping(value = "/message")
    public String message(@RequestHeader("first-request") String header) {
        log.info(header);
        return "Hello world in User Service";
    }

    @GetMapping(value = "/check")
    public String check() {
        log.info("check invoked");
        return "Hi, there. This is User service";
    }
}
