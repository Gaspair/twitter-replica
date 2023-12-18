package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLikeDTO {

    private UUID likeID;
    private UUID userID;
    private UUID tweetID;
    private LocalDateTime likeTimestamp;
}
