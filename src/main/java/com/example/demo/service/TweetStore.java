package com.example.demo.service;

import com.example.demo.model.Tweet;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface TweetStore {

    void saveTweetReply(Tweet tweet, String handle, String parentTweetId);

    List<Tweet> getTweetsByUserHandle(String handle);

    List <Tweet> getTweetsByTags(List<String> tags);

    Optional <Tweet> getTweetById(String tweetId);

    void saveTweet(Tweet tweet, String handle);



    ResponseEntity<String> likesCounterTweet(Tweet tweet, String userThatLikedTweet);

    ResponseEntity<String> statusUpdaterTweet(String tweetId);

    void deleteTweet(Optional<Tweet> tweet);
}
