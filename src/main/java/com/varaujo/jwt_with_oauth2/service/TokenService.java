package com.varaujo.jwt_with_oauth2.service;

import com.varaujo.jwt_with_oauth2.dto.LoginRequest;
import com.varaujo.jwt_with_oauth2.entity.Role;
import com.varaujo.jwt_with_oauth2.entity.User;
import com.varaujo.jwt_with_oauth2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Value("${jwt.token-duration}")
    private Long tokenDuration;

    @Value("${spring.application.name}")
    private String applicationName;

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public TokenService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    public String login(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUsername(loginRequest.username());
        if(user.isEmpty() || !isPasswordCorrect(loginRequest.password(), user.get().getPassword())) {
            throw new BadCredentialsException("incorrect username or password");
        }

        return generateToken(user.get());
    }

    private boolean isPasswordCorrect(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }

    private String generateToken(User user) {
        Instant now = Instant.now();

        String scope = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(applicationName)
                .subject(user.getUserId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(tokenDuration))
                .claim("scope", scope)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
