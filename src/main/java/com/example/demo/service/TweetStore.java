package com.example.demo.service;

import com.example.demo.model.Tweet;

import java.util.List;

public interface TweetStore {

    void saveTweet(Tweet tweet);

    List<Tweet> getTweetsByUserHandle(String user);
}
