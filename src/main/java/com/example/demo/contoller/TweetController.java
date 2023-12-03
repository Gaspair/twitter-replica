package com.example.demo.contoller;

import com.example.demo.model.Tweet;
import com.example.demo.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{tweetId}")
    public ResponseEntity<?> getTweetById(@PathVariable String tweetId) {
        Optional<Tweet> tweet = tweetService.getTweetById(tweetId);

        return tweet.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("")
    public List<Tweet> getTweetsByTags(@RequestParam("tags") List<String> tags) {
        return tweetService.getTweetsByTags(tags);
    }

    @PostMapping("/create/{handle}")
    public ResponseEntity<String> saveTweet(@RequestBody Tweet tweet, @PathVariable String handle, @RequestParam(required = false) String parentTweetId) {

        if (parentTweetId == null) {
            tweetService.saveTweet(tweet, handle);
            return new ResponseEntity<>("Tweet created", HttpStatus.CREATED);
        } else if (!tweetService.getTweetById(parentTweetId).isPresent()) {
            return new ResponseEntity<>("Tweet parent ID invalid", HttpStatus.NOT_FOUND);
        } else {
            if (tweetService.getTweetById(parentTweetId).get().getTweetStatus() == false) {
                return new ResponseEntity<>("Tweet is deactivated", HttpStatus.BAD_REQUEST);
            }
            tweetService.saveTweetReply(tweet, handle, parentTweetId);
            return new ResponseEntity<>("Tweet reply created", HttpStatus.CREATED);
        }
    }

    @PatchMapping("/{tweetId}")
    public ResponseEntity<String> tweetLikesCounter(@PathVariable String tweetId,@RequestParam String userThatLikedTweet) {
        Optional<Tweet> optionalTweet = tweetService.getTweetById(tweetId);

        if (optionalTweet.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tweet does not exist!");
        }
        Tweet tweet = optionalTweet.get();

        return tweetService.likesCounterTweet(tweet,userThatLikedTweet);
    }

    @PatchMapping("/changeStatus/{tweetId}")
    public ResponseEntity<String> tweetStatusUpdater(@PathVariable String tweetId) {
        return tweetService.statusUpdateTweet(tweetId);
    }


    @DeleteMapping("/delete/{tweetId}")
    public ResponseEntity<String> deleteTweet(@PathVariable String tweetId) {
        Optional<Tweet> optionalTweet = tweetService.getTweetById(tweetId);

        if (optionalTweet.isPresent()) {
            tweetService.deleteTweet(optionalTweet);
            return new ResponseEntity<>("Tweet deleted", HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting tweet");
        }

    }

}
