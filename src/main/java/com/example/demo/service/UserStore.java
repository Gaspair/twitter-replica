package com.example.demo.service;

import com.example.demo.model.User;

import java.util.UUID;

public interface UserStore {
    void save(User user);
    void delete(UUID id);
    User getUser(UUID uuid);
    Boolean userExists(String user);
    User update(User user);


}
