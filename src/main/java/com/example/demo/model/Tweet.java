package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tweet", schema = "twt")
public class Tweet {

    public Tweet() {
    }

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "tweet_id")
    private UUID tweetID;

    @ManyToOne
    @JsonIgnore
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

    @Column(name = "is_active", nullable = false)
    private Boolean isTweetActive;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "parent_tweet_id")
    private Tweet parentTweet;

    @OneToMany(mappedBy = "parentTweet", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Tweet> replies;


    @CreationTimestamp
    @Column(name="created_at", updatable = false)
    private final Date createdAt = new Date();

    public Boolean getTweetActive() {
        return isTweetActive;
    }

    public void setTweetActive(Boolean tweetActive) {
        isTweetActive = tweetActive;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

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
