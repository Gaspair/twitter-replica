package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.util.*;

@Entity
@Table(name = "tweet", schema = "twt")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tweet {

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

    @Column(name = "likes_count", columnDefinition = "integer default 0")
    private Integer likesCount = 0;


    @Column(name = "retweets_count", columnDefinition = "integer default 0")
    private Integer retweetCount = 0;

    @Column(name = "tweet_status", columnDefinition = "boolean default true")
    private Boolean tweetStatus = true;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "parent_tweet_id")
    private Tweet parentTweet;

    @OneToMany(mappedBy = "parentTweet", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Tweet> replies;


    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @OneToMany(mappedBy = "tweet")
    private Set<UserLike> userLikes;


}