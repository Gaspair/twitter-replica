package com.example.demo.service;

import com.example.demo.model.Tweet;

import java.util.List;

public interface TweetStore {

    void saveTweetReply(Tweet tweet, String handle, String parentTweetId);

    List<Tweet> getTweetsByUserHandle(String handle);

    Tweet getTweetById(String tweetId);

    void saveTweet(Tweet tweet, String handle);
}
