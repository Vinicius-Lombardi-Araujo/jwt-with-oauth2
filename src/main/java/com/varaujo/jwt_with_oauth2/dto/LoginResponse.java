package com.varaujo.jwt_with_oauth2.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
