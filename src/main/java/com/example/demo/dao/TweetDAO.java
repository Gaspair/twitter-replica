package com.example.demo.dao;


import com.example.demo.model.Tweet;
import com.example.demo.repository.TweetRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.TweetStore;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Transactional
public class TweetDAO implements TweetStore {
    private TweetRepo tweetRepo;
    private UserRepo userRepo;


    @Autowired
    public TweetDAO(TweetRepo tweetRepo,UserRepo userRepo){
        this.tweetRepo=tweetRepo;
        this.userRepo=userRepo;
    }


    @Override
    public void saveTweet(Tweet tweet) {
        tweetRepo.save(tweet);
    }

    @Override
    public List<Tweet> getTweetsByUserHandle(String handle) {
        return tweetRepo.findTweetsByUser(userRepo.findByHandle(handle));
    }




}
