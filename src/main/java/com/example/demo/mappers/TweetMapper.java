package com.example.demo.mappers;

import com.example.demo.dto.TweetDTO;
import com.example.demo.model.Tweet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = UserLikeMapper.class)
public interface TweetMapper {

    TweetMapper INSTANCE = Mappers.getMapper( TweetMapper.class );
    @Mappings({
            @Mapping(source = "tweet.tweetID", target = "tweetID"),
            @Mapping(source = "tweet.content", target = "content"),
            @Mapping(source = "tweet.tags", target = "tags"),
            @Mapping(source = "tweet.likesCount", target = "likesCount"),
            @Mapping(source = "tweet.retweetCount", target = "retweetCount"),
            @Mapping(source = "tweet.tweetStatus", target = "tweetStatus"),
//            @Mapping(source = "tweet.createdAt", target = "createdAt"),
            @Mapping(source = "tweet.parentTweet.tweetID", target = "parentTweetID"),
            @Mapping(source = "tweet.userLikes", target = "userLikes")
    })
    TweetDTO tweetToTweetDTO(Tweet tweet);

    @Mappings({
            @Mapping(source = "tweetDTO.tweetID", target = "tweetID"),
            @Mapping(source = "tweetDTO.content", target = "content"),
            @Mapping(source = "tweetDTO.tags", target = "tags"),
            @Mapping(source = "tweetDTO.likesCount", target = "likesCount"),
            @Mapping(source = "tweetDTO.retweetCount", target = "retweetCount"),
            @Mapping(source = "tweetDTO.tweetStatus", target = "tweetStatus"),
//            @Mapping(source = "tweetDTO.createdAt", target = "createdAt"),
            @Mapping(source = "tweetDTO.parentTweetID", target = "parentTweet.tweetID"),
            @Mapping(source = "tweetDTO.userLikes", target = "userLikes")
    })
    Tweet tweetDTOToTweet(TweetDTO tweetDTO);
}
