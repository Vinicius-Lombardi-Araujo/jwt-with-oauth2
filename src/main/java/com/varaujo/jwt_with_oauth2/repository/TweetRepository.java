package com.varaujo.jwt_with_oauth2.repository;

import com.varaujo.jwt_with_oauth2.entity.Tweet;
import com.varaujo.jwt_with_oauth2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
