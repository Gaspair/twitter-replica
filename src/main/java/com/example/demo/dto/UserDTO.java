package com.example.demo.dto;

import com.example.demo.model.PersonalInfo;
import com.example.demo.model.UserLike;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class UserDTO {

    private UUID userID;
    private String handle;
    @JsonIgnore
    private Date createdAt;
    @JsonIgnore
    private Date lastModifiedDate;

    private PersonalInfoDTO personalInfoDTO;
    private List<TweetDTO> tweets;



    public PersonalInfoDTO getPersonalInfoDTO() {
        return personalInfoDTO;
    }

    public void setPersonalInfoDTO(PersonalInfoDTO personalInfoDTO) {
        this.personalInfoDTO = personalInfoDTO;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }


    public List<TweetDTO> getTweets() {
        return tweets;
    }

    public void setTweets(List<TweetDTO> tweets) {
        this.tweets = tweets;
    }
}
