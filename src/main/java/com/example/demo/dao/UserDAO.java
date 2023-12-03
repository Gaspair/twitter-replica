package com.example.demo.dao;

import com.example.demo.dto.UserDTO;
import com.example.demo.mappers.UserMapper;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.UserStore;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Transactional
public class UserDAO implements UserStore {

    private UserRepo userRepo;
    private UserMapper userMapper;



    @Autowired
    public UserDAO(UserRepo userRepo,UserMapper userMapper ) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;

    }

    @Override
    public ResponseEntity<?> getUserByID(UUID userID) {
        Optional<User> userOptional = userRepo.findById(userID);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not found");
        }

        UserDTO userDTO = userMapper.userToUserDTO(userOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @Override
    public ResponseEntity<?> getOneUserByHandle(String handle) {
        Optional<User> userOptional = userRepo.findByHandle(handle);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not found");
        }

        UserDTO userDTO = userMapper.userToUserDTO(userOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body(userDTO);

    }



    @Override
    public ResponseEntity getUsersByHandle(String handle, int limit) {
        Optional<List<User>> userOptionalList = userRepo.findByHandleContaining(handle);
        if (userOptionalList.isPresent() && !userOptionalList.get().isEmpty()) {
            List<User> userList = userOptionalList.get();
            List<UserDTO> userListDTO = userList.stream()
                    .map(userMapper::userToUserDTO)
                    .limit(limit)
                    .toList();

            return ResponseEntity.status(HttpStatus.OK).body(userListDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of("No matches found."));
        }
    }


    @Override
    public ResponseEntity<?> saveUser(UserDTO userDTO) {
        Optional<User> handleVerify = userRepo.findByHandle(userDTO.getHandle());

        if(handleVerify.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Handle in use already");
        }

        User userToBeSaved = userMapper.userDTOtoUser(userDTO);
        userRepo.save(userToBeSaved);
        return ResponseEntity.status(HttpStatus.OK).body("User has been created");
    }


    @Override
    public ResponseEntity<?> deleteUser(UUID id) {

        Optional<User> userOptional = userRepo.findById(id);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not found");
        }

        userRepo.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("User has been deleted");
    }

}
