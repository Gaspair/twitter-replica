package com.example.demo.dao;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.UserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Component
@Transactional
public class UserDAO implements UserStore {

    private UserRepo userRepo;


    @Autowired
    public UserDAO(UserRepo userRepo ) {
        this.userRepo = userRepo;

    }

    @Override
    public void saveUser(User user) {
        userRepo.save(user);
    }

    @Override
    public void deleteUser(UUID id) {
    userRepo.deleteById(id);

    }

    @Override
    public User getUserByID(UUID uuid) {
        return userRepo.findById(uuid).get();
    }

    @Override
    public List<User> getUserByHandle(String handle) {
        return userRepo.findByHandleContaining(handle);
    }

    @Override
    public Boolean userExists(String userID) {
        return userRepo.existsById(UUID.fromString(userID));
    }

    @Override
    public User update(User user) {
        return null;
    }
}
