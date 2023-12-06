package com.example.demo.dao;


import com.example.demo.dto.TweetDTO;
import com.example.demo.dto.UserLikeDTO;
import com.example.demo.mappers.TweetMapper;
import com.example.demo.mappers.UserLikeMapper;
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
import java.util.Set;
import java.util.UUID;


@Component
@Transactional
public class TweetDAO implements TweetStore {
    private TweetRepo tweetRepo;
    private UserRepo userRepo;
    private UserLikeRepo userLikeRepo;
    private TweetMapper tweetMapper;

    private UserLikeMapper userLikeMapper;

    @Autowired
    public TweetDAO(TweetRepo tweetRepo, UserRepo userRepo, UserLikeRepo userLikeRepo, TweetMapper tweetMapper, UserLikeMapper userLikeMapper) {
        this.tweetRepo = tweetRepo;
        this.userRepo = userRepo;
        this.userLikeRepo = userLikeRepo;
        this.tweetMapper = tweetMapper;
        this.userLikeMapper = userLikeMapper;
    }


    @Override
    public ResponseEntity<?> getTweetById(UUID tweetId) {
        Optional<Tweet> tweetOptional = tweetRepo.findById(tweetId);

        if (tweetOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tweet not found");
        }

        TweetDTO tweetDTO = tweetMapper.tweetToTweetDTO(tweetOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(tweetDTO);
    }

    @Override
    public ResponseEntity<?> getTweetsByUserHandle(String handle) {
        Optional<User> userOptional = userRepo.findByHandle(handle);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOptional.get();

        List<Tweet> tweetList = tweetRepo.findByUser(user);

        List<TweetDTO> tweetDTOList = tweetList.stream().map(tweetMapper::tweetToTweetDTO).toList();

        return ResponseEntity.status(HttpStatus.OK).body(tweetDTOList);

    }


    @Override
    public ResponseEntity<?> getTweetsByTags(List<String> tags) {


        Optional<List<Tweet>> optionalTweets = tweetRepo.findAllByTagsIn(tags);

        if (optionalTweets.isPresent() && !optionalTweets.get().isEmpty()) {

            List<TweetDTO> tweetDTOList = optionalTweets.get().stream().map(tweetMapper::tweetToTweetDTO).toList();

            return ResponseEntity.status(HttpStatus.OK).body(tweetDTOList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of("No matches found."));
        }

    }

    @Override
    public ResponseEntity<?> saveTweet(TweetDTO tweetDTO, String handle, UUID parentTweetID) {
        Optional<User> userOptional = userRepo.findByHandle(handle);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not exist");
        }

        Tweet tweetToBeCreated = tweetMapper.tweetDTOToTweet(tweetDTO);
        tweetToBeCreated.setUser(userOptional.get());
        tweetToBeCreated.setLikesCount(0);
        tweetToBeCreated.setTweetStatus(true);
        tweetToBeCreated.setRetweetCount(0);

        if (parentTweetID != null) {
            Optional<Tweet> parentTweetOptional = tweetRepo.findById(parentTweetID);
            if (parentTweetOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parent tweet does not exist");
            }

            Tweet parentTweet = parentTweetOptional.get();
            tweetToBeCreated.setParentTweet(parentTweet);
        } else {
            tweetRepo.save(tweetToBeCreated);
        }

        tweetRepo.save(tweetToBeCreated);
        return ResponseEntity.status(HttpStatus.OK).body("Tweet created");
    }


    @Override
    public ResponseEntity<?> deleteTweet(UUID tweetID) {
        Optional<Tweet> optionalTweet = tweetRepo.findById(tweetID);

        if (optionalTweet.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tweet not found.");

        }

        tweetRepo.deleteById(tweetID);
        return ResponseEntity.status(HttpStatus.OK).body("Tweet has been deleted");
    }


    @Override
    public ResponseEntity<?> likesCounterTweet(UUID tweetID, String handle) {

        Optional<Tweet> optionalTweet = tweetRepo.findById(tweetID);

        if (optionalTweet.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tweet not found");
        }
        Tweet tweet = optionalTweet.get();

        if (Boolean.FALSE.equals(tweet.getTweetStatus())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tweet is deactivated");
        }

        Optional<User> optionalUser = userRepo.findByHandle(handle);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = optionalUser.get();



        Optional<UserLike> optionalUserLike = userLikeRepo.findUserLikeByUserAndTweet(user, tweet);

        if(optionalUserLike.isPresent()){

            userLikeRepo.delete(optionalUserLike.get());
            tweet.setLikesCount(tweet.getLikesCount() - 1);
            return ResponseEntity.status(HttpStatus.OK).body("Tweet disliked");
        }


        UserLike userLike = new UserLike();
        userLike.setUser(user);
        userLike.setTweet(tweet);

        tweet.setLikesCount(tweet.getLikesCount() + 1);
        tweetRepo.save(tweet);

        userLikeRepo.save(userLike);

        return ResponseEntity.status(HttpStatus.OK).body("Tweet liked");

    }


    @Override
    public ResponseEntity<?> statusUpdaterTweet(UUID tweetID) {
        Optional<Tweet> optionalTweet = tweetRepo.findById(tweetID);

        if (optionalTweet.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tweet not found");
        }

        Tweet tweet = optionalTweet.get();
        Boolean tweetCurrentStatus = tweet.getTweetStatus();

        tweet.setTweetStatus(!tweetCurrentStatus);
        tweetRepo.save(tweet);

        if (Boolean.TRUE.equals(tweetCurrentStatus)) {
            return ResponseEntity.status(HttpStatus.OK).body("The tweet has been deactivated");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("The tweet has been activated");
        }
    }

    @Override
    public ResponseEntity<?> getTweetLikes(UUID tweetID) {
        Optional<Tweet> optionalTweet = tweetRepo.findById(tweetID);

        if (optionalTweet.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tweet not found");
        }

        Tweet tweet = optionalTweet.get();

        Set<UserLike> tweetLikes = tweet.getUserLikes();

        if (!tweetLikes.isEmpty()) {

            List<UserLikeDTO> tweetDTOList = tweetLikes.stream().map(userLikeMapper::userLikeToUserLikeDTO).toList();

            return ResponseEntity.status(HttpStatus.OK).body(tweetDTOList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of("No likes yet"));
        }

    }


}
