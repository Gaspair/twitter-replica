package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TweetDTO {

    private UUID tweetID;
    private String content;
    private List<String> tags;
    private Integer likesCount;
    private Integer retweetCount;
    private Boolean tweetStatus;
    private Date createdAt;

}
