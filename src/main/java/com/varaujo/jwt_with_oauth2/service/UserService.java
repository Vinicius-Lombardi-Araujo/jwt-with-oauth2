package com.varaujo.jwt_with_oauth2.service;

import com.varaujo.jwt_with_oauth2.dto.CreateUserDto;
import com.varaujo.jwt_with_oauth2.entity.Role;
import com.varaujo.jwt_with_oauth2.entity.User;
import com.varaujo.jwt_with_oauth2.repository.RoleRepository;
import com.varaujo.jwt_with_oauth2.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createUser(CreateUserDto createUserDto) {
        Role basicRole = roleRepository.findByName(Role.Values.BASIC.name());
        Optional<User> userFromDb = userRepository.findByUsername(createUserDto.username());
        if(userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User user = new User();
        user.setUsername(createUserDto.username());
        user.setPassword(passwordEncoder.encode(createUserDto.password()));
        user.setRoles(Set.of(basicRole));

        return userRepository.save(user);
    }
}
