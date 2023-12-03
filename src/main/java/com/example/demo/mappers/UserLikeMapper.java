package com.example.demo.mappers;

import com.example.demo.dto.UserLikeDTO;
import com.example.demo.model.UserLike;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserLikeMapper {
    UserLikeMapper INSTANCE = Mappers.getMapper( UserLikeMapper.class );
    @Mapping(source = "userLike.likeID", target = "likeID")
    @Mapping(source = "userLike.user.userID", target = "userID")
    @Mapping(source = "userLike.tweet.tweetID", target = "tweetID")
    @Mapping(source = "userLike.likeTimestamp", target = "likeTimestamp")
    UserLikeDTO userLikeToUserLikeDTO(UserLike userLike);

    @Mapping(source = "userLikeDTO.likeID", target = "likeID")
    @Mapping(source = "userLikeDTO.userID", target = "user.userID")
    @Mapping(source = "userLikeDTO.tweetID", target = "tweet.tweetID")
    @Mapping(source = "userLikeDTO.likeTimestamp", target = "likeTimestamp")
    UserLike userLikeDTOToUserLike(UserLikeDTO userLikeDTO);
}

