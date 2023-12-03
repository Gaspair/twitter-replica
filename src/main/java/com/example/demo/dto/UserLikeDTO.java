package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserLikeDTO {

    private UUID likeID;
    private UUID userID;
    private UUID tweetID;
    private LocalDateTime likeTimestamp;

    public UUID getLikeID() {
        return likeID;
    }

    public void setLikeID(UUID likeID) {
        this.likeID = likeID;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public UUID getTweetID() {
        return tweetID;
    }

    public void setTweetID(UUID tweetID) {
        this.tweetID = tweetID;
    }

    public LocalDateTime getLikeTimestamp() {
        return likeTimestamp;
    }

    public void setLikeTimestamp(LocalDateTime likeTimestamp) {
        this.likeTimestamp = likeTimestamp;
    }
}
