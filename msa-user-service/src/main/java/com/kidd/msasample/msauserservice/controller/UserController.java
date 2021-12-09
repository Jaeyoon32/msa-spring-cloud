package com.kidd.msasample.msauserservice.controller;

import com.kidd.msasample.msauserservice.dto.UserDto;
import com.kidd.msasample.msauserservice.service.UserService;
import com.kidd.msasample.msauserservice.vo.Greeting;
import com.kidd.msasample.msauserservice.vo.RequestUser;
import com.kidd.msasample.msauserservice.vo.ResponseUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {

    private Environment env;
    private UserService userService;

    @Autowired
    private Greeting greeting;
    @Autowired
    public UserController(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }

    @GetMapping(value = "/health-check")
    public String status() {
        return "It's working in User service";
    }

    @GetMapping(value = "/welcome")
    public String welcome() {
        return greeting.getMessage();
        //return this.env.getProperty("greeting.message");
    }
//
//    @PostMapping
//    public String createUser(@RequestBody RequestUser user) {
//
//    }

    @PostMapping(value = "/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(user, UserDto.class);
        userService.createUser(userDto);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }
}
