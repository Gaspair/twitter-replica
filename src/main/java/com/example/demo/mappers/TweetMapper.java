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
            @Mapping(source = "tweet.tweetStatus", target = "tweetStatus"),
    })
    TweetDTO tweetToTweetDTO(Tweet tweet);

    @Mappings({
            @Mapping(source = "tweetDTO.tweetID", target = "tweetID"),
            @Mapping(source = "tweetDTO.content", target = "content"),
            @Mapping(source = "tweetDTO.tags", target = "tags"),
            @Mapping(source = "tweetDTO.tweetStatus", target = "tweetStatus"),
            @Mapping(source = "tweetDTO.likesCount", target = "likesCount"),
            @Mapping(source = "tweetDTO.retweetCount", target = "retweetCount"),
    })
    Tweet tweetDTOToTweet(TweetDTO tweetDTO);
}
