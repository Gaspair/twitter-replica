package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "userLike", schema = "like_list")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @CreationTimestamp
    @Column(name = "like_timestamp")
    private LocalDateTime likeTimestamp;



}
