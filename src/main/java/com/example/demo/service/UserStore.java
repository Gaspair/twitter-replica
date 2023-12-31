package com.example.demo.service;

import com.example.demo.dto.PersonalInfoDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserStore {

    ResponseEntity<?> getUserByID(UUID uuid);

    ResponseEntity<?> getOneUserByHandle(String handle);

    ResponseEntity<?> getUsersByHandle(String handle, int limit);

    ResponseEntity<?> saveUser(UserDTO userDTO);

    ResponseEntity<?> deleteUser(UUID id);

    ResponseEntity<?> updateUser(UUID userID, PersonalInfoDTO personalInfoDTO);


    ResponseEntity<?> getUserPersonalInfo(UUID userID);

    ResponseEntity<?> getLikesList(UUID userID);
}
