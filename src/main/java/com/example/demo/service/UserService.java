package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserStore store;

    @Autowired
    public  UserService(UserStore store){
        this.store = store;
    }

    public void save(User user){
        store.save(user);
    }

    public User getUser(UUID uuid){
        return store.getUser(uuid);
    }


}
