package com.example.demo.dao;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.UserStore;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<User> getUserByID(UUID uuid) {

        Optional<User> optionalUser = userRepo.findById(uuid);
        if(optionalUser.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.of(optionalUser);
    }

    @Override
    public List<User> getUsersByHandle(String handle) {
        return userRepo.findByHandleContaining(handle);
    }


    @Override
    public Boolean userExists(String userID) {
        return userRepo.existsById(UUID.fromString(userID));
    }

    @Override
    public void saveUser(User user) {
        userRepo.save(user);
    }


    @Override
    public void deleteUser(UUID id) {
        userRepo.deleteById(id);

    }
//    @Override
//    public void updateUser(User user) {
//        userRepo.updateUser(user);
//    }

    @Override
    public User getOneUserByHandle(String handle) {
        return userRepo.findByHandle(handle);
    }
}
