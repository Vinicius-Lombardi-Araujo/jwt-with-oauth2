package com.varaujo.jwt_with_oauth2.controller;

import com.varaujo.jwt_with_oauth2.dto.CreateUserDto;
import com.varaujo.jwt_with_oauth2.entity.User;
import com.varaujo.jwt_with_oauth2.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        return ResponseEntity.ok().build();
    }
}
