package com.example.demo.service;

import com.example.demo.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TweetService {

    private final TweetStore tweetStore;



    @Autowired
    public TweetService(TweetStore tweetStore) {
        this.tweetStore = tweetStore;

    }


    public void saveTweetReply(Tweet tweet, String handle, String parentTweetId) {
        tweetStore.saveTweetReply(tweet,handle,parentTweetId);
    }

    public Optional<Tweet> getTweetById(String id){
        return tweetStore.getTweetById(id);
    }


     public List <Tweet> getTweetsByTags(List<String> tags){
        return tweetStore.getTweetsByTags(tags);
     }

    public List<Tweet> getTweetsByUserHandle(String handle){
        return tweetStore.getTweetsByUserHandle(handle);


    }
    public void deleteTweet(Optional<Tweet> tweet){
        tweetStore.deleteTweet(tweet);
    }

    public void saveTweet(Tweet tweet, String handle) {
        tweetStore.saveTweet(tweet,handle);
    }
}
