package com.example.demo.dao;

import com.example.demo.dto.PersonalInfoDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserLikeDTO;
import com.example.demo.mappers.PersonalInfoMapper;
import com.example.demo.mappers.UserLikeMapper;
import com.example.demo.mappers.UserMapper;
import com.example.demo.model.PersonalInfo;
import com.example.demo.model.User;
import com.example.demo.model.UserLike;
import com.example.demo.repository.UserLikeRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserStore;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class UserDAO implements UserStore {

    private UserRepository userRepo;
    private UserMapper userMapper;
    private UserLikeRepository userLikeRepository;
    private final PersonalInfoMapper personalInfoMapper;
    private final UserLikeMapper userLikeMapper;


    @Autowired
    public UserDAO(UserRepository userRepo, UserMapper userMapper,
                   PersonalInfoMapper personalInfoMapper, UserLikeRepository userLikeRepository,
                   UserLikeMapper userLikeMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.userLikeRepository = userLikeRepository;
        this.personalInfoMapper = personalInfoMapper;
        this.userLikeMapper = userLikeMapper;
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
    public ResponseEntity<?> getUserPersonalInfo(UUID userID) {
        Optional<User> userOptional = userRepo.findById(userID);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOptional.get();

        PersonalInfo personalInfo = user.getPersonalInfo();
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.personalInfoToPersonalInfoDTO(personalInfo);

        return ResponseEntity.status(HttpStatus.OK).body(personalInfoDTO);
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
        List<User> userList = userRepo.findByHandleContaining(handle);
        if (!userList.isEmpty() ) {
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
    public ResponseEntity<?> getLikesList(UUID userID) {
        Optional<User> optionalUser = userRepo.findById(userID);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        User user = optionalUser.get();

        List<UserLike> userLikeList = userLikeRepository.findUserLikeByUser(user);

        List<UserLikeDTO> userLikeDTOList = userLikeList.stream().map(userLikeMapper::userLikeToUserLikeDTO).toList();

        return ResponseEntity.status(HttpStatus.OK).body(userLikeDTOList);
    }

    @Override
    public ResponseEntity<?> saveUser(UserDTO userDTO) {
        Optional<User> handleVerify = userRepo.findByHandle(userDTO.getHandle());

        if (handleVerify.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Handle in use already");
        }
        User userToBeSaved = userMapper.userDTOtoUser(userDTO);

        PersonalInfoDTO personalInfoDTO = userDTO.getPersonalInfoDTO();
        if (personalInfoDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Personal info is null");
        }
        userToBeSaved.setPersonalInfo(personalInfoMapper.personalInfoDTOToPersonalInfo(personalInfoDTO));
        userRepo.save(userToBeSaved);
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.userToUserDTO(userToBeSaved));
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


        PersonalInfo updatedPersonalInfo = personalInfoMapper.updatePersonalInfoFromDTO(existingPersonalInfo, personalInfoDTO);
        userToBeSaved.setPersonalInfo(updatedPersonalInfo);

        userRepo.save(userToBeSaved);

        return ResponseEntity.status(HttpStatus.OK).body("Personal information updated");
    }


}
