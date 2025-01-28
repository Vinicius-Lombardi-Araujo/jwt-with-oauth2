package com.varaujo.jwt_with_oauth2.controller;

import com.varaujo.jwt_with_oauth2.dto.CreateTweetDto;
import com.varaujo.jwt_with_oauth2.dto.FeedDto;
import com.varaujo.jwt_with_oauth2.service.TweetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateTweetDto createTweetDto, JwtAuthenticationToken token) {
        tweetService.create(createTweetDto, token);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long tweetId, JwtAuthenticationToken token) {
        tweetService.delete(tweetId, token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/feed")
    public ResponseEntity<FeedDto> feed(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        FeedDto feedDto = tweetService.feed(page, pageSize);
        return ResponseEntity.ok(feedDto);
    }

}
