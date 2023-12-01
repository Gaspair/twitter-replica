package com.example.demo.dao;


import com.example.demo.model.Tweet;
import com.example.demo.model.User;
import com.example.demo.repository.TweetRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.TweetStore;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
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
    public Optional<Tweet> getTweetById(String tweetId) {
        return tweetRepo.findById(UUID.fromString(tweetId));
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
        tweetRepo.save(tweet);
    }

    @Override
    public ResponseEntity<String> likesCounterTweet(Tweet tweet, String operationType){
        Integer likesCount = tweet.getLikesCount();

        if(Boolean.FALSE.equals(tweet.getTweetActive())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tweet is deactivated");
        }

        if(operationType.equals("increment")){
            tweet.setLikesCount( likesCount + 1);
            this.saveTweet(tweet, tweet.getUser().getHandle());
            return ResponseEntity.status(HttpStatus.OK).body("Likes count updated +");
        }else{
            tweet.setLikesCount( likesCount - 1);
            this.saveTweet(tweet, tweet.getUser().getHandle());
            return ResponseEntity.status(HttpStatus.OK).body("Likes count updated -");
        }
    }

    @Override
    public ResponseEntity<String> statusUpdaterTweet(String tweetId) {
        Optional<Tweet> optionalTweet = this.getTweetById(tweetId);

        if(optionalTweet.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tweet not found");
        }

        Tweet tweet = optionalTweet.get();
        Boolean tweetCurrentStatus = tweet.getTweetActive();
        tweet.setTweetActive(!tweetCurrentStatus);
        this.saveTweet(tweet, tweet.getUser().getHandle());

        if (Boolean.TRUE.equals(tweetCurrentStatus)) {
            return ResponseEntity.status(HttpStatus.OK).body("The tweet has been deactivated");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("The tweet has been activated");
        }
    }

    @Override
    public void deleteTweet(Optional<Tweet> tweet) {
        tweetRepo.deleteTweetByTweetID(tweet.get().getTweetID());
    }
}
