package com.varaujo.jwt_with_oauth2.controller;

import com.varaujo.jwt_with_oauth2.dto.LoginRequest;
import com.varaujo.jwt_with_oauth2.dto.LoginResponse;
import com.varaujo.jwt_with_oauth2.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Value("${jwt.token-duration}")
    private Long tokenDuration;

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = tokenService.login(loginRequest);
        return ResponseEntity.ok(new LoginResponse(token, tokenDuration));
    }
}
