package com.example.demo.model;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="tweet",schema= "twt")
public class Tweet {

    @Id
    @Type(type="uuid-char")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name="tweet_id")
    private UUID tweetID;

}
