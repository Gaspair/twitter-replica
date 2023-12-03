package com.example.demo.dao;


import com.example.demo.model.Tweet;
import com.example.demo.model.User;
import com.example.demo.model.UserLike;
import com.example.demo.repository.UserLikeRepo;
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

    private UserLikeRepo userLikeRepo;

    @Autowired
    public TweetDAO(TweetRepo tweetRepo, UserRepo userRepo,UserLikeRepo userLikeRepo) {
        this.tweetRepo = tweetRepo;
        this.userRepo = userRepo;
        this.userLikeRepo = userLikeRepo;
    }

    //    @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
// GET REQUESTS BELOW"
    @Override
    public List<Tweet> getTweetsByUserHandle(String handle) {
        Optional<User> userOptional = userRepo.findByHandle(handle);


        User user = userOptional.get();

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
    public List<Tweet> getTweetsByTags(List<String> tags) {
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
        Optional<User> userOptional = userRepo.findByHandle(handle);


        User user = userOptional.get();


        tweet.setParentTweet(tweetRepo.getReferenceById(UUID.fromString(parentTweetId)));


        if (user != null) {
            tweet.setUser(user);
        }

        tweetRepo.save(tweet);
    }


    @Override
    public void saveTweet(Tweet tweet, String handle) {
        Optional<User> userOptional = userRepo.findByHandle(handle);


        User user = userOptional.get();

        if (user != null) {
            tweet.setUser(user);
        } else {
            throw new IllegalArgumentException("User does not exist!");
        }
        tweetRepo.save(tweet);
    }

    @Override
    public ResponseEntity<String> likesCounterTweet(Tweet tweet, String userThatLikedTweet) {

        if (Boolean.FALSE.equals(tweet.getTweetStatus())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tweet is deactivated");
        }

        Optional<User> optionalUser = userRepo.findByHandle(userThatLikedTweet);

        if(optionalUser.isEmpty()){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = optionalUser.get();

        // Create a new UserLike entity
        UserLike userLike = new UserLike();
        userLike.setUser(user);
        userLike.setTweet(tweet);

        // Save the UserLike entity
        userLikeRepo.save(userLike);

        return ResponseEntity.status(HttpStatus.OK).body("Tweet Liked");

    }


//    @Override
//    public ResponseEntity<String> likesCounterTweet(Tweet tweet, String userThatLikedTweet) {
//        Integer likesCount = tweet.getLikesCount();
//
//        if (Boolean.FALSE.equals(tweet.getTweetActive())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tweet is deactivated");
//        }
//
//        // Check if the user exists
//        User user = userRepo.findByHandle(userThatLikedTweet);
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//        }
//
//        try {
//            tweet.getU
//
//
//            List<UserTweetLike> userLikes = tweet.getUserLikes();
//
//            userLikes.add(userTweetLike);
//            tweet.setUserLikes(userLikes);
//
//            // Update the likes count
//            tweet.setLikesCount(likesCount + 1);
//
//            // Save the tweet, assuming you have a method like this
//            this.saveTweet(tweet, tweet.getUser().getHandle());
//
//            return ResponseEntity.status(HttpStatus.OK).body("Tweet liked successfully");
//        } catch (Exception e) {
//            // Handle specific exceptions if needed
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
//        }
//    }

    @Override
    public ResponseEntity<String> statusUpdaterTweet(String tweetId) {
        Optional<Tweet> optionalTweet = this.getTweetById(tweetId);

        if (optionalTweet.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tweet not found");
        }

        Tweet tweet = optionalTweet.get();
        Boolean tweetCurrentStatus = tweet.getTweetStatus();
        tweet.setTweetStatus(!tweetCurrentStatus);
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
