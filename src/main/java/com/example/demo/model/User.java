package com.example.demo.model;



import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user", schema = "usr")
public class User {

    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private UUID id;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_info_id", referencedColumnName = "personal_info_id")
    private PersonalInfo personalInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tweet_id", referencedColumnName = "tweet_id")
    private Tweet tweet;

}
