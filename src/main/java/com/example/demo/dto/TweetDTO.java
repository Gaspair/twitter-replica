package com.example.demo.dto;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TweetDTO {

    private UUID tweetID;
    private String content;
    private List<String> tags;
    private Integer likesCount;
    private Integer retweetCount;
    private Boolean tweetStatus;
    private Date createdAt;
    private UUID parentTweetID;

    private Set<UserLikeDTO> userLikes;


    public Set<UserLikeDTO> getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(Set<UserLikeDTO> userLikes) {
        this.userLikes = userLikes;
    }

    public UUID getTweetID() {
        return tweetID;
    }

    public void setTweetID(UUID tweetID) {
        this.tweetID = tweetID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Integer getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(Integer retweetCount) {
        this.retweetCount = retweetCount;
    }

    public Boolean getTweetStatus() {
        return tweetStatus;
    }

    public void setTweetStatus(Boolean tweetStatus) {
        this.tweetStatus = tweetStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public UUID getParentTweetID() {
        return parentTweetID;
    }

    public void setParentTweetID(UUID parentTweetID) {
        this.parentTweetID = parentTweetID;
    }
}
