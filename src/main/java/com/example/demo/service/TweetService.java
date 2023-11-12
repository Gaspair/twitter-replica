package com.example.demo.service;

import com.example.demo.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Tweet  getTweetById(String id){
        return tweetStore.getTweetById(id);
    }


    public List<Tweet> getTweetsByUserHandle(String handle){
        return tweetStore.getTweetsByUserHandle(handle);


    }


    public void saveTweet(Tweet tweet, String handle) {
        tweetStore.saveTweet(tweet,handle);
    }
}
