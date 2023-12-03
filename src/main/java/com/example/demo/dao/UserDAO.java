package com.example.demo.dao;

import com.example.demo.dto.PersonalInfoDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.mappers.PersonalInfoMapper;
import com.example.demo.mappers.UserMapper;
import com.example.demo.model.PersonalInfo;
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
    private final PersonalInfoMapper personalInfoMapper;


    @Autowired
    public UserDAO(UserRepo userRepo,UserMapper userMapper,
                   PersonalInfoMapper personalInfoMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;

        this.personalInfoMapper = personalInfoMapper;
    }

    @Override
    public ResponseEntity<?> getUserByID(UUID userID) {
        Optional<User> userOptional = userRepo.findById(userID);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not found");
        }

        User user = userOptional.get();

        UserDTO userDTO = userMapper.userToUserDTO(user);

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

        PersonalInfoDTO personalInfoDTO = userDTO.getPersonalInfoDTO();
        if (personalInfoDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Personal info is null");
        }
        userToBeSaved.setPersonalInfo(personalInfoMapper.personalInfoDTOToPersonalInfo(personalInfoDTO));
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

    @Override
    public ResponseEntity<?> updateUser(UUID userID, PersonalInfoDTO personalInfoDTO) {
        Optional<User> userOptional = userRepo.findById(userID);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User userToBeSaved = userOptional.get();
        PersonalInfo existingPersonalInfo = userToBeSaved.getPersonalInfo();

        if (existingPersonalInfo == null) {
            // If there is no existing personal info, create a new one
            userToBeSaved.setPersonalInfo(personalInfoMapper.personalInfoDTOToPersonalInfo(personalInfoDTO));
        } else {
            // Update only the specified fields
            PersonalInfo updatedPersonalInfo = personalInfoMapper.updatePersonalInfoFromDTO(existingPersonalInfo, personalInfoDTO);
            userToBeSaved.setPersonalInfo(updatedPersonalInfo);
        }

        userRepo.save(userToBeSaved);

        return ResponseEntity.status(HttpStatus.OK).body("Personal information updated");
    }


    @Override
    public ResponseEntity<?> getUserPersonalInfo(UUID userID) {
        Optional<User> userOptional = userRepo.findById(userID);

        if(userOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOptional.get();

        PersonalInfo personalInfo = user.getPersonalInfo();
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.personalInfoToPersonalInfoDTO(personalInfo);

        return ResponseEntity.status(HttpStatus.OK).body(personalInfoDTO);
    }

}
