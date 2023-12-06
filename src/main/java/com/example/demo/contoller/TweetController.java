package com.example.demo.contoller;

import com.example.demo.dto.TweetDTO;
import com.example.demo.model.Tweet;
import com.example.demo.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/tweet")
public class TweetController {

    private TweetService tweetService;

    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping("/{tweetId}")
    public ResponseEntity<?> getTweetById(@PathVariable UUID tweetId) {
        return tweetService.getTweetById(tweetId);
    }


    @GetMapping("/user/{handle}")
    public ResponseEntity<?> getTweetsByUserHandle(@PathVariable String handle) {
        return tweetService.getTweetsByUserHandle(handle);
    }


    @GetMapping("")
    public ResponseEntity<?> getTweetsByTags(@RequestParam("tags") List<String> tags) {
        return tweetService.getTweetsByTags(tags);
    }

    @PostMapping("/create/{handle}")
    public ResponseEntity<?> saveTweet(@RequestBody TweetDTO tweetDTO, @PathVariable String handle, @RequestParam(required = false) UUID parentTweetID) {
        return tweetService.saveTweet(tweetDTO, handle, parentTweetID);
    }


    @DeleteMapping("/delete/{tweetID}")
    public ResponseEntity<?> deleteTweet(@PathVariable UUID tweetID) {
        return tweetService.deleteTweet(tweetID);
    }


    @PatchMapping("/{tweetID}")
    public ResponseEntity<?> tweetLikesCounter(@PathVariable UUID tweetID, @RequestParam String handle) {
        return tweetService.likesCounterTweet(tweetID,handle);
    }

    @PatchMapping("/changeStatus/{tweetID}")
    public ResponseEntity<?> tweetStatusUpdater(@PathVariable UUID tweetID) {
        return tweetService.statusUpdaterTweet(tweetID);
    }

    @GetMapping("/likes/{tweetID}")
    public ResponseEntity<?> tweetLikes(@PathVariable UUID tweetID){
        return tweetService.getTweetLikes(tweetID);
    }
}


