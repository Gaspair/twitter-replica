package com.example.demo.dao;


import com.example.demo.model.Tweet;
import com.example.demo.model.TweetStatusType;
import com.example.demo.model.User;
import com.example.demo.repository.TweetRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.TweetStore;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
@Transactional
public class TweetDAO implements TweetStore {
    private TweetRepo tweetRepo;
    private UserRepo userRepo;


    @Autowired
    public TweetDAO(TweetRepo tweetRepo, UserRepo userRepo) {
        this.tweetRepo = tweetRepo;
        this.userRepo = userRepo;
    }

    //    @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
// GET REQUESTS BELOW"
    @Override
    public List<Tweet> getTweetsByUserHandle(String handle) {
        User user = userRepo.findByHandle(handle);

        if (user != null) {
            // Get all tweets associated with the user
            return tweetRepo.findByUser(user);
        } else {
            // Handle the case where the user is not found
            // You might want to return a 404 status code or handle it differently based on your requirements
            return null;
        }
    }

    @Override
    public List <Tweet> getTweetsByTags(List<String> tags){
        return tweetRepo.findAllByTagsIn(tags);
    }

    @Override
    public Tweet getTweetById(String tweetId) {
        return tweetRepo.getReferenceById(UUID.fromString(tweetId));
    }
//    @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


    // POST REQUESTS BELOW
//    @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    @Override
    public void saveTweetReply(Tweet tweet, String handle, String parentTweetId) {
        User user = userRepo.findByHandle(handle);


        tweet.setParentTweet(tweetRepo.getReferenceById(UUID.fromString(parentTweetId)));


        if (user != null) {
            tweet.setUser(user);
        }
        tweet.setTweetActive(true);
        tweetRepo.save(tweet);
    }


    @Override
    public void saveTweet(Tweet tweet, String handle) {
        User user = userRepo.findByHandle(handle);

        if (user != null) {
            tweet.setUser(user);
        }else{
            throw new IllegalArgumentException("User does not exist!");
        }
        tweet.setTweetActive(true);
        tweetRepo.save(tweet);
    }

    @Override
    public void deleteTweet(Tweet tweet) {
        tweetRepo.deleteTweetByTweetID(tweet.getTweetID());
    }
}
