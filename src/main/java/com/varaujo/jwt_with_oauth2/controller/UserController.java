package com.varaujo.jwt_with_oauth2.controller;

import com.varaujo.jwt_with_oauth2.dto.CreateUserDto;
import com.varaujo.jwt_with_oauth2.entity.User;
import com.varaujo.jwt_with_oauth2.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<User>> list() {
        return ResponseEntity.ok(userService.list());
    }

}
