package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserStore store;

    @Autowired
    public UserService(UserStore store){
        this.store = store;
    }

    public void saveUser(User user){
        store.save(user);
    }

    public User getUserById(UUID uuid){
        return store.getUserByID(uuid);
    }
    public List<User> getUserByHandle(String string){
        return store.getUserByHandle(string);
    }


}
