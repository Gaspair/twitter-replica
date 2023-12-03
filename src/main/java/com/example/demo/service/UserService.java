package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserStore store;

    @Autowired
    public UserService(UserStore store){
        this.store = store;
    }


    public ResponseEntity<?> saveUser(UserDTO userDTO){
        return  store.saveUser(userDTO);
    }
    public ResponseEntity<?> getUserById(UUID uuid){
        return store.getUserByID(uuid);
    }
    public ResponseEntity<?> getUsersByHandle(String string, int limit){
        return store.getUsersByHandle(string,  limit);
    }


    public ResponseEntity<?> deleteUser(UUID userID){
       return store.deleteUser(userID);
    }

    public ResponseEntity<?> getOneUserByHandle(String handle) {
        return store.getOneUserByHandle(handle);
    }
}
