package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;
import java.util.UUID;

public interface UserStore {
    void saveUser(User user);
    void deleteUser(UUID id);
    User getUserByID(UUID uuid);

    List<User> getUsersByHandle(String handle);
    Boolean userExists(String user);
//    void updateUser(User user);


    User getOneUserByHandle(String handle);
}
