package com.kidd.msasample.msauserservice.service;

import com.kidd.msasample.msauserservice.dto.UserDto;
import com.kidd.msasample.msauserservice.repository.UserEntity;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll();
}
