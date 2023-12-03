package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "userLike", schema = "like_list")
public class UserLike {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "like_id")
    private UUID likeID;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tweet_id")
    private Tweet tweet;

    @Column(name = "like_timestamp")
    private LocalDateTime likeTimestamp;

    public UserLike() {
        this.likeTimestamp = LocalDateTime.now();
    }


    public UUID getLikeID() {
        return likeID;
    }

    public void setLikeID(UUID likeID) {
        this.likeID = likeID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    public LocalDateTime getLikeTimestamp() {
        return likeTimestamp;
    }

    public void setLikeTimestamp(LocalDateTime likeTimestamp) {
        this.likeTimestamp = likeTimestamp;
    }
}
