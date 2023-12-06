package com.example.demo.service;

import com.example.demo.dto.TweetDTO;
import com.example.demo.model.Tweet;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TweetStore {

    ResponseEntity<?> getTweetById(UUID tweetID);

    ResponseEntity<?> getTweetsByUserHandle(String handle);

    ResponseEntity<?> getTweetsByTags(List<String> tags);

    ResponseEntity<?> saveTweet(TweetDTO tweet, String handle, UUID parentTweetID);


    ResponseEntity<?> deleteTweet(UUID tweetID);


    ResponseEntity<?> likesCounterTweet(UUID tweetID, String userThatLikedTweet);

    ResponseEntity<?> statusUpdaterTweet(UUID tweetID);


    ResponseEntity<?> getTweetLikes(UUID tweetID);
}
