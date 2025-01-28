package com.varaujo.jwt_with_oauth2.service;

import com.varaujo.jwt_with_oauth2.dto.CreateTweetDto;
import com.varaujo.jwt_with_oauth2.entity.Tweet;
import com.varaujo.jwt_with_oauth2.entity.User;
import com.varaujo.jwt_with_oauth2.repository.TweetRepository;
import com.varaujo.jwt_with_oauth2.repository.UserRepository;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

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
}
