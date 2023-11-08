package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;
import java.util.UUID;

public interface UserStore {
    void saveUser(User user);
    void deleteUser(UUID id);
    User getUserByID(UUID uuid);

    List<User> getUserByHandle(String handle);
    Boolean userExists(String user);
    User update(User user);


}
