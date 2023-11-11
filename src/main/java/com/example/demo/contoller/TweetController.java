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

    @PostMapping("/create")
    public ResponseEntity<String> saveTweet( @RequestBody Tweet tweet) {
        tweetService.saveTweet(tweet);
        return new ResponseEntity<>("Tweet created", HttpStatus.CREATED);
    }

    @GetMapping("/user/{handle}")
    public ResponseEntity<List<Tweet>> getTweetsByUserHandle(@PathVariable String handle) {
        List<Tweet> tweets = tweetService.getTweetsByUserHandle(handle);

        if (tweets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(tweets, HttpStatus.OK);
        }
    }

}
