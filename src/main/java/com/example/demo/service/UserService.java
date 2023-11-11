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
        store.saveUser(user);
    }
    public User getUserById(UUID uuid){
        return store.getUserByID(uuid);
    }
    public List<User> getUsersByHandle(String string){
        return store.getUsersByHandle(string);
    }


    public void deleteUser(User user){
        store.deleteUser(user.getId());
    }

    public User getOneUserByHandle(String handle) {
        return store.getOneUserByHandle(handle);
    }
}
