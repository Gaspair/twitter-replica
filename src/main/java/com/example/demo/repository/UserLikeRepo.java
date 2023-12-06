package com.example.demo.repository;

import com.example.demo.model.Tweet;
import com.example.demo.model.User;
import com.example.demo.model.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserLikeRepo extends JpaRepository<UserLike, UUID> {
   Optional<List<UserLike>> findUserLikeByUser(User user);


   Optional<UserLike> findUserLikeByUserAndTweet(User user, Tweet tweet);
}
