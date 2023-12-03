package com.example.demo.mappers;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Optional;


@Mapper(componentModel = "spring", uses = {PersonalInfoMapper.class, TweetMapper.class})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "user.userID", target = "userID")
    @Mapping(source = "user.handle", target = "handle")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(source = "user.personalInfo", target = "personalInfoDTO")
    @Mapping(source = "user.tweets", target = "tweets")
    UserDTO userToUserDTO(User user);

    @Mapping(source = "userDTO.userID", target = "userID")
    @Mapping(source = "userDTO.handle", target = "handle")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(source = "userDTO.personalInfoDTO", target = "personalInfo")
    @Mapping(source = "userDTO.tweets", target = "tweets")
    User userDTOtoUser(UserDTO userDTO);
}
