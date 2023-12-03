package com.example.demo.repository;

import com.example.demo.model.Tweet;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface TweetRepo extends JpaRepository<Tweet, UUID> {
    List<Tweet> findAllByTagsIn(List<String> tags);
    List<Tweet> findByUser(User user);
    void deleteTweetByTweetID(UUID tweetId);

}
