package com.example.demo.api.repository;

import com.example.demo.model.PersonalInfo;
import com.example.demo.model.Tweet;
import com.example.demo.model.User;
import com.example.demo.repository.TweetRepository;
import com.example.demo.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TweetRepositoryTests {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TweetRepository tweetRepo;


    @Test
    @DirtiesContext
    public void TweetRepository_SaveTweet_ReturnSavedTweet() {
        // Arrange
        PersonalInfo personalInfo = PersonalInfo.builder()
                .email("Testing@yahoo.com")
                .married(true)
                .firstName("Ned")
                .lastName("Stark").build();
        User user = User.builder()
                .handle("TheWinterIsComing")
                .personalInfo(personalInfo)
                .build();
        User savedUser = userRepo.save(user);

        Tweet tweet = Tweet.builder().content("I like cats").user(savedUser).build();


        Tweet savedTweet = tweetRepo.save(tweet);

        // Assertions
        Assertions.assertThat(savedTweet).isNotNull();
        Assertions.assertThat(savedTweet.getContent()).isNotNull();
    }

    @Test
    @DirtiesContext
    public void TweetRepository_SaveTweetReply_ReturnSavedReply() {
        // Arrange
        PersonalInfo personalInfo = PersonalInfo.builder()
                .email("Testing@yahoo.com")
                .married(true)
                .firstName("Ned")
                .lastName("Stark").build();
        User user = User.builder()
                .handle("TheWinterIsComing")
                .personalInfo(personalInfo)
                .build();

        PersonalInfo personalInfo1 = PersonalInfo.builder()
                .email("Testing@yahoo.com")
                .married(true)
                .firstName("Robert")
                .lastName("Baratheon").build();
        User user1 = User.builder()
                .handle("TheKing")
                .personalInfo(personalInfo1)
                .build();
        User savedUser = userRepo.save(user);
        User savedUser1 = userRepo.save(user1);

        Tweet tweet = Tweet.builder().content("I like wolves").user(savedUser).build();
        Tweet savedTweet = tweetRepo.save(tweet);


        Tweet tweetReply = Tweet.builder().content("I don't like hogs!").user(savedUser1).parentTweet(savedTweet).build();
        Tweet savedReply = tweetRepo.save(tweetReply);

        // Assertions
        Assertions.assertThat(savedReply).isNotNull();
        Assertions.assertThat(savedReply.getContent()).isNotNull();
    }

    @Test
    @DirtiesContext
    public void TweetRepository_FindByID_ReturnOneTweet() {
        // Arrange
        PersonalInfo personalInfo = PersonalInfo.builder()
                .email("Testing@yahoo.com")
                .married(true)
                .firstName("Ned")
                .lastName("Stark").build();
        User user = User.builder()
                .handle("TheWinterIsComing")
                .personalInfo(personalInfo)
                .build();
        User savedUser = userRepo.save(user);

        Tweet tweet = Tweet.builder().content("I like wolves").user(savedUser).build();


        Tweet savedTweet = tweetRepo.save(tweet);

        Tweet foundTweet = tweetRepo.findById(savedTweet.getTweetID()).get();

        // Assertions
        Assertions.assertThat(foundTweet).isNotNull();
        Assertions.assertThat(foundTweet.getContent()).isEqualTo("I like wolves");
    }


    @Test
    @DirtiesContext
    public void TweetRepository_FindByTags_ReturnAllThatMatch() {
        // Arrange
        PersonalInfo personalInfo = PersonalInfo.builder()
                .email("Testing@yahoo.com")
                .married(true)
                .firstName("Ned")
                .lastName("Stark").build();
        User user = User.builder()
                .handle("TheWinterIsComing")
                .personalInfo(personalInfo)
                .build();

        PersonalInfo personalInfo1 = PersonalInfo.builder()
                .email("Testing@yahoo.com")
                .married(true)
                .firstName("Robert")
                .lastName("Baratheon").build();
        User user1 = User.builder()
                .handle("TheKing")
                .personalInfo(personalInfo1)
                .build();
        User savedUser = userRepo.save(user);
        User savedUser1 = userRepo.save(user1);


        Tweet tweet = Tweet.builder().content("I like wolves").user(savedUser).tags(List.of("WolvesRule","WinterIsComing")).build();
        Tweet tweet1 = Tweet.builder().content("I like stags").user(savedUser1).tags(List.of("StagsAreTheBest","IHateHogs")).build();


        Tweet savedTweet = tweetRepo.save(tweet);

        List<Tweet> tweetList = tweetRepo.findAllByTagsIn(List.of("WolvesRule"));


        // Assertions
        Assertions.assertThat(tweetList).isNotEmpty();
        Assertions.assertThat(tweetList).hasSize(1);
    }

}
