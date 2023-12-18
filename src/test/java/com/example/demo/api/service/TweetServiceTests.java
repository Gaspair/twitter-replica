package com.example.demo.api.service;

import com.example.demo.repository.TweetRepository;
import com.example.demo.service.TweetStore;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TweetServiceTests {
    @Mock
    private TweetRepository tweetRepository;

    @InjectMocks
    private TweetStore tweetService;

    @Test
    public void TweetService_CreateTweet_ReturnsTweetDTO(){

    }

}
