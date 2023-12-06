package com.example.demo.service;

import com.example.demo.dto.TweetDTO;
import com.example.demo.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TweetService {

    private final TweetStore tweetStore;


    @Autowired
    public TweetService(TweetStore tweetStore) {
        this.tweetStore = tweetStore;

    }

    public ResponseEntity<?> getTweetById(UUID tweetID) {
        return tweetStore.getTweetById(tweetID);
    }


    public ResponseEntity<?> getTweetsByTags(List<String> tags) {
        return tweetStore.getTweetsByTags(tags);
    }

    public  ResponseEntity<?> getTweetsByUserHandle(String handle) {
        return tweetStore.getTweetsByUserHandle(handle);
    }

    public ResponseEntity<?> deleteTweet(UUID tweetID) {
       return tweetStore.deleteTweet(tweetID);
    }

    public ResponseEntity<?> likesCounterTweet(UUID tweetID, String userThatLikedTweet) {
        return tweetStore.likesCounterTweet(tweetID, userThatLikedTweet);
    }

    public ResponseEntity<?> statusUpdaterTweet(UUID tweetID) {
        return tweetStore.statusUpdaterTweet(tweetID);
    }

    public ResponseEntity<?> saveTweet(TweetDTO tweetDTO, String handle, UUID parentTweetID) {
        return tweetStore.saveTweet(tweetDTO, handle, parentTweetID);
    }

    public ResponseEntity<?> getTweetLikes(UUID tweetID) {
        return tweetStore.getTweetLikes(tweetID);
    }
}
