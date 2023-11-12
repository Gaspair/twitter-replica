package com.example.demo.contoller;

import com.example.demo.model.Tweet;
import com.example.demo.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tweet")
public class TweetController {

    private TweetService tweetService;



    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping("/user/{handle}")
    public ResponseEntity<List<Tweet>> getTweetsByUserHandle(@PathVariable String handle) {
        List<Tweet> tweets = tweetService.getTweetsByUserHandle(handle);

        if (tweets.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(tweets);
        }
    }


    @PostMapping("/reply/{handle}/{parentTweetId}")
    public ResponseEntity<String> saveTweetReply(@RequestBody Tweet tweet, @PathVariable String handle, @PathVariable String parentTweetId) {
        tweetService.saveTweetReply(tweet,handle,parentTweetId);
        return new ResponseEntity<>("Tweet created", HttpStatus.CREATED);
    }


    @PostMapping("/create/{handle}")
    public ResponseEntity<String> saveTweet(@RequestBody Tweet tweet, @PathVariable String handle) {
        tweetService.saveTweet(tweet,handle);
        return new ResponseEntity<>("Tweet created", HttpStatus.CREATED);
    }




}
