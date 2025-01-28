package com.varaujo.jwt_with_oauth2.service;

import com.varaujo.jwt_with_oauth2.dto.CreateTweetDto;
import com.varaujo.jwt_with_oauth2.entity.Role;
import com.varaujo.jwt_with_oauth2.entity.Tweet;
import com.varaujo.jwt_with_oauth2.entity.User;
import com.varaujo.jwt_with_oauth2.repository.TweetRepository;
import com.varaujo.jwt_with_oauth2.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public TweetService(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    public Tweet create(CreateTweetDto createTweetDto, JwtAuthenticationToken token) {
        Optional<User> user  = userRepository.findById(UUID.fromString(token.getName()));

        Tweet tweet = new Tweet();
        tweet.setUser(user.get());
        tweet.setContent(createTweetDto.content());

        tweetRepository.save(tweet);

        return tweetRepository.save(tweet);
    }

    @Transactional
    public void delete(Long tweetId, JwtAuthenticationToken token) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Optional<User> user = userRepository.findById(UUID.fromString(token.getName()));
        Boolean isAdmin = user.get().getRoles()
                .stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));

        if(!isAdmin && !tweet.getUser().getUserId().equals(UUID.fromString(token.getName()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        tweetRepository.deleteById(tweetId);
    }
}
