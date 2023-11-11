package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tweet", schema = "twt")
@JsonIgnoreProperties({"replies"})
public class Tweet {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "tweet_id")
    private UUID tweetID;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User user;

    @Column(name = "content")
    private String content;

    @ElementCollection
    private List<String> tags;

    @Column(name = "likes_count")
    private Integer likesCount;

    @Column(name = "retweets_count")
    private Integer retweetCount;

    @Column(name = "is_active",columnDefinition = "boolean default false")
    private boolean isActive;



    @ManyToOne
    @JoinColumn(name = "parent_tweet_id",nullable = true)
    @JsonBackReference
    private Tweet parentTweet;

    @JsonManagedReference
    @OneToMany(mappedBy = "parentTweet", cascade = CascadeType.ALL)
    private List<Tweet> replies;



    public UUID getTweetID() {
        return tweetID;
    }

    public void setTweetID(UUID tweetID) {
        this.tweetID = tweetID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Tweet getParentTweet() {
        return parentTweet;
    }

    public void setParentTweet(Tweet parentTweet) {
        this.parentTweet = parentTweet;
    }

    public List<Tweet> getReplies() {
        return replies;
    }

    public void setReplies(List<Tweet> replies) {
        this.replies = replies;
    }
}
