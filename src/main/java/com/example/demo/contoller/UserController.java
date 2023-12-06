package com.example.demo.contoller;

import com.example.demo.dto.PersonalInfoDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneByUUID(@PathVariable("id") UUID userID) {
        return service.getUserById(userID);
    }

    @GetMapping("/profile/{handle}")
    public ResponseEntity<?> getOneByHandle(@PathVariable("handle") String handle) {
        return service.getOneUserByHandle(handle);
    }

    @GetMapping("/search/{handle}")
    public ResponseEntity<?> getUsersByHandle(@PathVariable("handle") String handle, @RequestParam(defaultValue = "5") int limit) {
        return service.getUsersByHandle(handle, limit);
    }

    @GetMapping("/personalInfo/{userID}")
    public ResponseEntity<?> getUsersPersonalInfo(@PathVariable("userID") UUID userID) {
        return service.getUserPersonalInfo(userID);
    }

    @GetMapping("/likeList/{userID}")
    public ResponseEntity<?> getLikesList(@PathVariable("userID") UUID userID) {
        return service.getLikesList(userID);
    }

    @PostMapping("")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO) {
        return service.saveUser(userDTO);
    }

    @PatchMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") UUID userID,@RequestBody PersonalInfoDTO personalInfoDTO) {
        return service.updateUser(userID,personalInfoDTO);

    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") UUID userID) {
        return service.deleteUser(userID);
    }
}
