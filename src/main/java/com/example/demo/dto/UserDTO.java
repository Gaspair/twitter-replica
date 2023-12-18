package com.example.demo.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private UUID userID;
    private String handle;
    @JsonIgnore
    private Date createdAt;
    @JsonIgnore
    private Date lastModifiedDate;

    private PersonalInfoDTO personalInfoDTO;

    private List<TweetDTO> tweets;

}
