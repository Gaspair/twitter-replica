package com.example.demo.service;

import com.example.demo.model.Tweet;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TweetService {

    private final TweetStore tweetStore;



    @Autowired
    public TweetService(TweetStore tweetStore,UserRepo userRepo) {
        this.tweetStore = tweetStore;

    }


    public void saveTweet( Tweet tweet) {
        tweetStore.saveTweet(tweet);
    }


    public List<Tweet> getTweetsByUserHandle(String handle){
        return tweetStore.getTweetsByUserHandle(handle);
    }


}
